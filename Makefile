# vim: set noexpandtab:
# =============================================================================
# Swagger-Codegen : Jenkins Makefile (for releases and uploading to aws)
# =============================================================================

# -----------------------------------------------------------------------------
# Public (jenkins) targets
# -----------------------------------------------------------------------------
.PHONY : release
release : 
	# -------------------------------------------------------------------
	# [SWAGGER-CODEGEN] release : use the build-swagger-codegen image b/c it already has the swagger dependencies
	# loaded into it's .m2/repository cache. Note that this command uses docker, and in order for the Jenkins
	# build agent to be able to pull the image from our private ECR bucket, the process has to be logged into
	# aws. So the Jenkis job looks like this:
	#
	#	eval `docker run mesosphere/aws-cli ecr get-login --no-include-email --region us-west-2`
	#	make release
	#
	# This is the release target, so this target inherits the creds, pulls the image, and runs the mvn commands.
	# -------------------------------------------------------------------
	docker run -v $(abspath .):/code 186612847456.dkr.ecr.us-west-2.amazonaws.com/ms/build-swagger-codegen:0.0.1 /bin/bash -c "cd /code && mvn clean package"

# vim: set noexpandtab:
# =============================================================================
# Swagger-Codegen : Jenkins Makefile (for releases and uploading to aws)
# =============================================================================

# -----------------------------------------------------------------------------
# Public (jenkins) targets
# -----------------------------------------------------------------------------
.PHONY : build
release : 
	# -------------------------------------------------------------------
	# [SWAGGER-CODEGEN] build
	# -------------------------------------------------------------------
	docker run -v $(abspath .):/code 186612847456.dkr.ecr.us-west-2.amazonaws.com/ms/build-swagger-codegen:0.0.1 /bin/bash -c "cd /code && mvn clean package"

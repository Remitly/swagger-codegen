#!/bin/bash

	(cd deps/swagger-codegen && forge run purge1); \
	if [ "$?" != "0" ] ; then \
		(cd deps/swagger-codegen && forge run purge2); \
		if [ "$?" != "0" ] ; then \
			(cd deps/swagger-codegen && forge run purge3); \
			if [ "$?" != "0" ] ; then exit 1; fi; \
		fi; \
	fi;

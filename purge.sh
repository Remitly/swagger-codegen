#!/bin/bash

	forge run purge1; \
	if [ "$?" != "0" ] ; then \
		forge run purge2; \
		if [ "$?" != "0" ] ; then \
			forge run purge3; \
			if [ "$?" != "0" ] ; then exit 1; fi; \
		fi; \
	fi;

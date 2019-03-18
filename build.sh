#!/bin/bash

	forge build; \
	if [ "$?" != "0" ] ; then \
		forge run build2; \
		if [ "$?" != "0" ] ; then \
			forge run build3; \
			if [ "$?" != "0" ] ; then exit 1; fi; \
		fi; \
	fi;

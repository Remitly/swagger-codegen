package io.swagger.codegen.options;

import io.swagger.codegen.CodegenConstants;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class GoClientOptionsProvider implements OptionsProvider {

    public static final String PACKAGE_VERSION_VALUE = "1.0.0";
    public static final boolean WITH_XML_VALUE = true;
    public static final String API_VERSION = "0.0.1";
    public static final String SERVER_PORT_VALUE = "8080";
    public static final String SERVICE_NAME_VALUE = "awesomeservice";

    @Override
    public String getLanguage() {
        return "go";
    }

    @Override
    public Map<String, String> createOptions() {
        ImmutableMap.Builder<String, String> builder = new ImmutableMap.Builder<String, String>();
        return builder
                .put(CodegenConstants.PACKAGE_VERSION, PACKAGE_VERSION_VALUE)
                .put(CodegenConstants.HIDE_GENERATION_TIMESTAMP, "true")
                .put(CodegenConstants.WITH_XML, "true")
                .put(CodegenConstants.API_VERSION, API_VERSION)
                .put(CodegenConstants.SERVER_PORT, SERVER_PORT_VALUE)
                .put(CodegenConstants.SERVICE_NAME, SERVICE_NAME_VALUE)
                .build();
    }

    @Override
    public boolean isServer() {
        return false;
    }
}

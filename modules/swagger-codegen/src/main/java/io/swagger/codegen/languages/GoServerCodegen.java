package io.swagger.codegen.languages;

import io.swagger.codegen.*;
import io.swagger.models.properties.ArrayProperty;
import io.swagger.models.properties.MapProperty;
import io.swagger.models.properties.Property;
import io.swagger.models.parameters.Parameter;

import java.io.File;
import java.util.*;

import org.apache.commons.lang3.StringUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoServerCodegen extends AbstractGoCodegen {

    public GoServerCodegen() {
        super();

        // set the output folder here
        outputFolder = "generated-code/go";

        /*
         * Models.  You can write model files using the modelTemplateFiles map.
         * if you want to create one template for file, you can do so here.
         * for multiple files for model, just put another entry in the `modelTemplateFiles` with
         * a different extension
         */
        modelTemplateFiles.put(
                "model.mustache",
                ".go");

        /*
         * Api classes.  You can write classes for each Api file with the apiTemplateFiles map.
         * as with models, add multiple entries with different extensions for multiple files per
         * class
         */
        apiTemplateFiles.put(
                "controller-api.mustache",   // the template to use
                ".go");       // the extension for each file to write

        /*
         * Template Location.  This is the location which templates will be read from.  The generator
         * will use the resource stream to attempt to read the templates.
         */
        embeddedTemplateDir = templateDir = "go-server";

        /*
         * Reserved words.  Override this with reserved words specific to your language
         */
        setReservedWordsLowerCase(
            Arrays.asList(
                // data type
                "string", "bool", "uint", "uint8", "uint16", "uint32", "uint64",
                "int", "int8", "int16", "int32", "int64", "float32", "float64",
                "complex64", "complex128", "rune", "byte", "uintptr",

                "break", "default", "func", "interface", "select",
                "case", "defer", "go", "map", "struct",
                "chan", "else", "goto", "package", "switch",
                "const", "fallthrough", "if", "range", "type",
                "continue", "for", "import", "return", "var", "error", "nil")
                // Added "error" as it's used so frequently that it may as well be a keyword
        );
    }

    @Override
    public void processOpts() {
        super.processOpts();

        if (additionalProperties.containsKey(CodegenConstants.PACKAGE_NAME)) {
            setPackageName((String) additionalProperties.get(CodegenConstants.PACKAGE_NAME));
        }
        else {
            setPackageName("swagger");
        }

        if (additionalProperties.containsKey(CodegenConstants.MODEL_PACKAGE)) {
            setModelPackage((String) additionalProperties.get(CodegenConstants.MODEL_PACKAGE));
        }
        else {
            setModelPackage("swagger");
        }

        /*
         * Supporting Files.  You can write single files for the generator with the
         * entire object tree available.  If the input file has a suffix of `.mustache
         * it will be processed by the template engine.  Otherwise, it will be copied
         *
         * Example generated file layout:
         *
         * ├── generated
         * │   ├── models
         * │   │   ├── model_department_listings.go
         * │   │   ├── model_department_summary.go
         * │   │   ├── model_job_details.go
         * │   │   └── model_job.go
         * │   └── runtime
         * │       ├── bootstrap.go
         * │       └── routers.go
         *
         */
        supportingFiles.add(new SupportingFile("swagger.mustache", "api", "swagger.yaml"));
        supportingFiles.add(new SupportingFile("main.mustache", "", "main.go"));
        supportingFiles.add(new SupportingFile("config.mustache", "service", "config.go"));
        supportingFiles.add(new SupportingFile("health.mustache", "service", "health.go"));
        supportingFiles.add(new SupportingFile("bootstrap.mustache",modelPackage() + File.separator + "runtime", "bootstrap.go"));
        supportingFiles.add(new SupportingFile("routers.mustache", modelPackage() + File.separator + "runtime", "routers.go"));
        supportingFiles.add(new SupportingFile("go-mod.mustache",  "go.mod"));
    }

    @Override
    public String apiPackage() {
        return ((String) additionalProperties.get(CodegenConstants.API_PATH));
    }

    /**
     * Configures the type of generator.
     *
     * @return the CodegenType for this generator
     * @see io.swagger.codegen.CodegenType
     */
    @Override
    public CodegenType getTag() {
        return CodegenType.SERVER;
    }

    /**
     * Configures a friendly name for the generator.  This will be used by the generator
     * to select the library with the -l flag.
     *
     * @return the friendly name for the generator
     */
    @Override
    public String getName() {
        return "go-server";
    }

    /**
     * Returns human-friendly help for the generator.  Provide the consumer with help
     * tips, parameters here
     *
     * @return A string value for the help message
     */
    @Override
    public String getHelp() {
        return "Generates a Go server library using the swagger-tools project.  By default, " +
                "it will also generate service classes--which you can disable with the `-Dnoservice` environment variable.";
    }

    /**
     * Location to write api files.  You can use the apiPackage() as defined when the class is
     * instantiated
     */
    @Override
    public String apiFileFolder() {
        return outputFolder + File.separator + apiPackage().replace('.', File.separatorChar);
    }

    @Override
    public String modelFileFolder() {
        return outputFolder + File.separator + modelPackage().replace('.', File.separatorChar) + File.separator + "models";
    }

}

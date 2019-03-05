package io.swagger.codegen.mustache;

import com.samskivert.mustache.Mustache;
import com.samskivert.mustache.Template;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;


public class DumpLambda implements Mustache.Lambda {
    public void execute(Template.Fragment frag, Writer out) throws IOException {
        Object ctx = frag.context();
        try {
            if (ctx instanceof Map) {
                out.write(dumpMap(frag.execute(), (Map<Object, Object>) ctx));
            }
        } catch (Exception ignored2) {
            try {
                out.write("context (reflection): " + ToStringBuilder.reflectionToString(ctx));

            } catch (Exception ignored3) {
                out.write("context (tostr): " + ctx.toString());
            }
        }
    }

    private String dumpMap(String prefix, Map<Object, Object> m) {
        TreeMap<String, String> tree = createTree(m);
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : tree.entrySet()) {
            sb.append(String.format("%s %s [ %s ]\n", prefix, entry.getKey(), entry.getValue()));
        }
        return sb.toString();
    }

    private TreeMap<String, String> createTree(Map<Object, Object> m) {
        TreeMap<String, String> tm = new TreeMap<>();
        for (Map.Entry entry : m.entrySet()) {
            String entryKey = entry.getKey().toString();
            Object entryValue = entry.getValue();
            if (entryValue instanceof Map) {
                TreeMap<String, String> branch = createTree((Map<Object, Object>) entryValue);
                for (Map.Entry leafEntry : branch.entrySet()) {
                    tm.put(String.format("%s:%s", entryKey, leafEntry.getKey()), leafEntry.getValue().toString());
                }
            } else {
                tm.put(entry.getKey().toString(), entry.getValue().toString());
            }
        }
        return tm;
    }
}

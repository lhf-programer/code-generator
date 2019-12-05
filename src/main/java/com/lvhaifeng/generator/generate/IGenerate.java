package com.lvhaifeng.generator.generate;

import java.util.Map;

public interface IGenerate {
    Map<String, Object> a() throws Exception;

    void generateCodeFile() throws Exception;

    void generateCodeFile(String var1, String var2) throws Exception;
}

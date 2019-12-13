package com.lvhaifeng.generator.generate;

import java.util.List;
import java.util.Map;

public interface IGenerate {
    Map<String, Object> a() throws Exception;

    void generateCodeFile(List<Boolean> isSelects) throws Exception;
}

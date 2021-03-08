package io.github.lmikoto.railgun.configurable;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author liuyang
 * 2021/3/7 7:22 下午
 */
@Data
@NoArgsConstructor
public class CodeGroup {

    private String name;

    private List<CodeDir> dirs;

    public static CodeGroup fromName(String name){
        CodeGroup codeGroup = new CodeGroup();
        codeGroup.setName(name);
        return codeGroup;
    }
}

package io.github.lmikoto.railgun.configurable;

import lombok.Data;

import java.util.List;

/**
 * @author liuyang
 * 2021/3/7 7:23 下午
 */
@Data
public class CodeDir {

    private String name;

    private List<CodeTemplate> templates;
}

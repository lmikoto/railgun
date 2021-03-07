package io.github.lmikoto.railgun.configurable;

import lombok.Data;

import java.util.List;

/**
 * @author liuyang
 * 2021/3/7 7:22 下午
 */
@Data
public class CodeGroup {

    private String name;

    private List<CodeDir> dirs;
}

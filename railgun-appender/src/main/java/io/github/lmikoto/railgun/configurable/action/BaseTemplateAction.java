package io.github.lmikoto.railgun.configurable.action;

import com.intellij.ui.treeStructure.Tree;
import io.github.lmikoto.railgun.configurable.TemplateConfigurable;

import javax.swing.tree.DefaultMutableTreeNode;

/**
 * @author liuyang
 * 2021/3/7 7:46 下午
 */
public abstract class BaseTemplateAction {


    private final Tree templateTree;

    public BaseTemplateAction(TemplateConfigurable configurable){
        templateTree = configurable.getTemplateTree();
    }

    protected DefaultMutableTreeNode getSelectedNode() {
        return (DefaultMutableTreeNode) this.templateTree.getLastSelectedPathComponent();
    }
}

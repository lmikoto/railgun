package io.github.lmikoto.railgun.configurable.action;

import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import io.github.lmikoto.railgun.configurable.TemplateConfigurable;

import javax.swing.tree.DefaultMutableTreeNode;
import java.util.ArrayList;
import java.util.List;

/**
 * @author liuyang
 * 2021/3/7 7:46 下午
 */
public class TemplateAddAction extends BaseTemplateAction implements AnActionButtonRunnable {
    public TemplateAddAction(TemplateConfigurable configurable) {
        super(configurable);
    }

    @Override
    public void run(AnActionButton anActionButton) {
        DefaultMutableTreeNode selectedNode = getSelectedNode();
    }

    private List<AnAction> getMultipleActions(DefaultMutableTreeNode selectedNode) {
        // 初始化所有的AnAction
        List<AnAction> actions = new ArrayList<>();
        CodeGroupAddAction rootAction = new CodeGroupAddAction();
//        CodeGroupAddAction groupAction = new CodeGroupAddAction();
//        CodeTemplateAddAction templateAction = new CodeTemplateAddAction();

        if (selectedNode == null) {
            actions.add(rootAction);
            return actions;
        }
//
//        Object object = selectedNode.getUserObject();
//        // 1. 如果是选中的root, 则可以新增root和group
//        if (object instanceof CodeRoot) {
//            actions.add(rootAction);
//            actions.add(groupAction);
//        }
//        // 2. 如果选中的是group, 则可以新增root, group以及template
//        if (object instanceof CodeGroup) {
//            actions.add(rootAction);
//            actions.add(groupAction);
//            actions.add(templateAction);
//        }
//        // 3. 如果选中的是template, 则可以新增root, group以及template
//        if (object instanceof CodeTemplate) {
//            actions.add(rootAction);
//            actions.add(groupAction);
//            actions.add(templateAction);
//        }
        return actions;
    }

    class CodeGroupAddAction extends AnAction {

        public CodeGroupAddAction() {
            super("Code Group", null, AllIcons.Nodes.JavaModule);
        }

        @Override
        public void actionPerformed(AnActionEvent anActionEvent) {
//            // 1. 显示root的新增dialog
//            TemplateRootEditDialog dialog = new TemplateRootEditDialog();
//            dialog.setTitle("Create New Root");
//            dialog.getButtonOK().addActionListener(e -> {
//                // 获取名称
//                String name = dialog.getNameField().getText();
//                if (StringUtils.isBlank(name)) {
//                    showErrorBorder(dialog.getNameField(), true);
//                    return;
//                }
//                // 新增root
//                addCodeRoot(CodeRoot.fromName(name.trim()));
//                dialog.setVisible(false);
//            });
//            // 2. 显示dialog
//            showDialog(dialog, 300, 150);
        }
    }
}

package io.github.lmikoto.railgun.configurable.action;

import com.intellij.icons.AllIcons;
import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DefaultActionGroup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.ui.AnActionButton;
import com.intellij.ui.AnActionButtonRunnable;
import io.github.lmikoto.railgun.configurable.CodeGroup;
import io.github.lmikoto.railgun.configurable.NameEditDialog;
import io.github.lmikoto.railgun.configurable.TemplateConfigurable;
import io.github.lmikoto.railgun.utils.CollectionUtils;

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
        final DefaultMutableTreeNode selectedNode = getSelectedNode();
        List<AnAction> actions = getMultipleActions(selectedNode);
        if (CollectionUtils.isEmpty(actions)) {
            return;
        }

        DefaultActionGroup group = new DefaultActionGroup(actions);
        JBPopupFactory.getInstance()
                .createActionGroupPopup(null, group, DataManager.getInstance().getDataContext(anActionButton.getContextComponent()),
                        JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, true).show(anActionButton.getPreferredPopupPoint());
    }

    private List<AnAction> getMultipleActions(DefaultMutableTreeNode selectedNode) {
        List<AnAction> actions = new ArrayList<>();
        CodeGroupAddAction groupAction = new CodeGroupAddAction();
//        CodeGroupAddAction groupAction = new CodeGroupAddAction();
//        CodeTemplateAddAction templateAction = new CodeTemplateAddAction();

        if (selectedNode == null) {
            actions.add(groupAction);
            return actions;
        }

        Object object = selectedNode.getUserObject();
        if (object instanceof CodeGroup) {
            actions.add(groupAction);
//            actions.add(groupAction);
        }
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
            NameEditDialog dialog = new NameEditDialog();
            dialog.setTitle("Create Group");
            dialog.getButtonOK().addActionListener(e -> {
                String name = dialog.getNameField().getText();
//                if (StringUtils.isBlank(name)) {
//                    showErrorBorder(dialog.getNameField(), true);
//                    return;
//                }
                addGroup(CodeGroup.fromName(name.trim()));
                dialog.setVisible(false);
            });
            showDialog(dialog, 300, 150);
        }
    }
}

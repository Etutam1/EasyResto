package UI;

import Model.EasyRestoDB;
import Model.Intermediaria;
import Model.Worker;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
/**
 *
 * @author matut
 */
public class EasyRestoInterface extends javax.swing.JFrame {

    /**
     * Creates new form Interfaz
     */
    public EasyRestoInterface() {
        initComponents();
        this.setLocationRelativeTo(null);
        this. changeComponentVisibility(this.adminBackgroundPanel, false);
        this. changeComponentVisibility(this.workerPasswordPanel, false);
        this.claseIntermedia.getWorkerNameButton();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        workerBackgroundPanel = new javax.swing.JPanel();
        workersPanel = new javax.swing.JPanel();
        workerPasswordPanel = new javax.swing.JPanel();
        passwordButtonPanelTextField = new javax.swing.JPasswordField();
        enterPasswordLabel = new javax.swing.JLabel();
        loginButton2 = new javax.swing.JButton();
        workerNameLabel = new javax.swing.JLabel();
        passwordPanelBackButton = new javax.swing.JButton();
        adminSettingsButton = new javax.swing.JButton();
        adminBackgroundPanel = new javax.swing.JPanel();
        adminPanel = new javax.swing.JPanel();
        loginTitleLabel = new javax.swing.JLabel();
        emailTextField = new javax.swing.JTextField();
        emailLabel = new javax.swing.JLabel();
        passTextField = new javax.swing.JPasswordField();
        passLabel = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        adminPanelBackButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        workerBackgroundPanel.setBackground(new java.awt.Color(0, 112, 115));
        workerBackgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        workersPanel.setBackground(new java.awt.Color(255, 255, 255));
        workersPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        workerBackgroundPanel.add(workersPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 120, 345, 510));

        workerPasswordPanel.setBackground(new java.awt.Color(0, 112, 115));
        workerPasswordPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        passwordButtonPanelTextField.setBackground(new java.awt.Color(255, 255, 255));
        passwordButtonPanelTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordButtonPanelTextFieldActionPerformed(evt);
            }
        });
        workerPasswordPanel.add(passwordButtonPanelTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 270, 190, -1));

        enterPasswordLabel.setForeground(new java.awt.Color(255, 255, 255));
        enterPasswordLabel.setText("CONTRASEÑA:");
        workerPasswordPanel.add(enterPasswordLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 250, 180, -1));

        loginButton2.setForeground(new java.awt.Color(51, 51, 51));
        loginButton2.setText("LOGIN");
        loginButton2.setBorder(null);
        loginButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButton2ActionPerformed(evt);
            }
        });
        workerPasswordPanel.add(loginButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 320, 70, 20));

        workerNameLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        workerNameLabel.setForeground(new java.awt.Color(255, 255, 255));
        workerNameLabel.setText("      WORKER");
        workerPasswordPanel.add(workerNameLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 120, 260, 50));

        passwordPanelBackButton.setForeground(new java.awt.Color(51, 51, 51));
        passwordPanelBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/img/1.png"))); // NOI18N
        passwordPanelBackButton.setBorder(null);
        passwordPanelBackButton.setPreferredSize(new java.awt.Dimension(60, 60));
        passwordPanelBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passwordPanelBackButtonActionPerformed(evt);
            }
        });
        workerPasswordPanel.add(passwordPanelBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 610, 60, 60));

        workerBackgroundPanel.add(workerPasswordPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, 610, 680));

        adminSettingsButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/img/admin.png"))); // NOI18N
        adminSettingsButton.setPreferredSize(new java.awt.Dimension(60, 60));
        adminSettingsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminSettingsButtonActionPerformed(evt);
            }
        });
        workerBackgroundPanel.add(adminSettingsButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 660, -1, -1));

        getContentPane().add(workerBackgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 750));

        adminBackgroundPanel.setBackground(new java.awt.Color(0, 112, 115));
        adminBackgroundPanel.setAutoscrolls(true);
        adminBackgroundPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        adminPanel.setBackground(new java.awt.Color(0, 112, 115));
        adminPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        loginTitleLabel.setFont(new java.awt.Font("Segoe UI", 0, 36)); // NOI18N
        loginTitleLabel.setForeground(new java.awt.Color(255, 255, 255));
        loginTitleLabel.setText("EASYRESTO");
        adminPanel.add(loginTitleLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, -1, 35));

        emailTextField.setBackground(new java.awt.Color(255, 255, 255));
        emailTextField.setForeground(new java.awt.Color(0, 0, 0));
        emailTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                emailTextFieldActionPerformed(evt);
            }
        });
        adminPanel.add(emailTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, 210, -1));

        emailLabel.setForeground(new java.awt.Color(255, 255, 255));
        emailLabel.setText("EMAIL");
        adminPanel.add(emailLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 150, -1, -1));

        passTextField.setBackground(new java.awt.Color(255, 255, 255));
        passTextField.setForeground(new java.awt.Color(0, 0, 0));
        passTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passTextFieldActionPerformed(evt);
            }
        });
        adminPanel.add(passTextField, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 240, 210, -1));

        passLabel.setForeground(new java.awt.Color(255, 255, 255));
        passLabel.setText("PASS");
        adminPanel.add(passLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 220, 40, -1));

        loginButton.setForeground(new java.awt.Color(51, 51, 51));
        loginButton.setText("LOGIN");
        loginButton.setBorder(null);
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });
        adminPanel.add(loginButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 310, 80, 20));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText(" ADMIN ZONE");
        adminPanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 50, 80, 30));

        adminBackgroundPanel.add(adminPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 370, 450));

        adminPanelBackButton.setForeground(new java.awt.Color(255, 255, 255));
        adminPanelBackButton.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Resources/img/1.png"))); // NOI18N
        adminPanelBackButton.setBorder(null);
        adminPanelBackButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminPanelBackButtonActionPerformed(evt);
            }
        });
        adminBackgroundPanel.add(adminPanelBackButton, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 660, 60, 60));

        getContentPane().add(adminBackgroundPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 670, 750));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        this.claseIntermedia.adminSettingsLogin(this.emailTextField.getText(), this.passTextField.getPassword());
        this.emptyPassFieldText(this.passTextField);
    }//GEN-LAST:event_loginButtonActionPerformed

    private void passTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passTextFieldActionPerformed
        this.claseIntermedia.adminSettingsLogin(this.emailTextField.getText(), this.passTextField.getPassword());
        this.emptyPassFieldText(this.passTextField);
    }//GEN-LAST:event_passTextFieldActionPerformed

    private void emailTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_emailTextFieldActionPerformed
        this.passTextField.requestFocusInWindow();
    }//GEN-LAST:event_emailTextFieldActionPerformed

    private void passwordButtonPanelTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordButtonPanelTextFieldActionPerformed
        this.claseIntermedia.workerLogin(this.workerNameLabel.getText(), this.passwordButtonPanelTextField.getPassword());
        this.emptyPassFieldText(this.passwordButtonPanelTextField);
    }//GEN-LAST:event_passwordButtonPanelTextFieldActionPerformed

    private void loginButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButton2ActionPerformed
        this.claseIntermedia.workerLogin(this.workerNameLabel.getText(), this.passwordButtonPanelTextField.getPassword());
        this.emptyPassFieldText(this.passwordButtonPanelTextField);
    }//GEN-LAST:event_loginButton2ActionPerformed
                  
    private void passwordPanelBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passwordPanelBackButtonActionPerformed
        this. changeComponentVisibility(this.workerPasswordPanel, false);
        this. changeComponentVisibility(this.workersPanel, true);
        this.emptyPassFieldText(this.passwordButtonPanelTextField);
        this.changeComponentVisibility(this.adminSettingsButton, true);
        
    }//GEN-LAST:event_passwordPanelBackButtonActionPerformed

    private void adminPanelBackButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminPanelBackButtonActionPerformed
        this. changeComponentVisibility(this.adminBackgroundPanel, false);
        this. changeComponentVisibility(this.workerBackgroundPanel, true);
        
    }//GEN-LAST:event_adminPanelBackButtonActionPerformed

    private void adminSettingsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminSettingsButtonActionPerformed
        this. changeComponentVisibility(this.workerBackgroundPanel, false);
        this. changeComponentVisibility(this.adminBackgroundPanel, true);
    }//GEN-LAST:event_adminSettingsButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(EasyRestoInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(EasyRestoInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(EasyRestoInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(EasyRestoInterface.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EasyRestoInterface().setVisible(true);

            }
        });
    }

    /**
     *
     * @param panel
     * @param button
     */
    private void addButtonToPanel(JPanel panel, JButton button) {
        panel.add(button);
    }

    public void configWorkerButton(String workerName) {
        JButton workerButton = new JButton(workerName);
        workerButton.setPreferredSize(new Dimension(80, 60));
        this.addActionListenerToButton(workerButton);
        this.addButtonToPanel(this.workersPanel, workerButton);
    }

    private void addActionListenerToButton(JButton button) {
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 changeComponentVisibility(workersPanel, false);
                 changeComponentVisibility(workerPasswordPanel, true);
                configWorkerNameLabel(e);
                passwordButtonPanelTextField.requestFocus();
                changeComponentVisibility(adminSettingsButton, true);
            }
        });
    }

    private void configWorkerNameLabel(ActionEvent event) {
        workerNameLabel.setText(((JButton) event.getSource()).getText());
        workerNameLabel.setHorizontalAlignment(JLabel.CENTER);
    }

   

    private void  changeComponentVisibility(JPanel panel, boolean visibility) {
        panel.setVisible(visibility);

    }
    
     private void changeComponentVisibility(Component component, boolean visibility) {
        component.setVisible(visibility);

    }

    public void emptyPassFieldText(JPasswordField passwordField) {
        passwordField.setText("");
    }
    
    public boolean checkEmptyWorkerPassField(String password) throws HeadlessException {
        return password.isBlank();
    }

    public boolean checkEmptyAdminLoginFields(String email, String password) throws HeadlessException {
        return password.isBlank() || email.isBlank();
    }


    public JPanel getBackgroundPanel() {
        return adminBackgroundPanel;
    }

    public void setBackgroundPanel(JPanel backgroundPanel) {
        this.adminBackgroundPanel = backgroundPanel;
    }

    public JLabel getEmailLabel() {
        return emailLabel;
    }

    public void setEmailLabel(JLabel emailLabel) {
        this.emailLabel = emailLabel;
    }

    public JTextField getEmailTextField() {
        return emailTextField;
    }

    public void setEmailTextField(JTextField emailTextField) {
        this.emailTextField = emailTextField;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JPanel getLoginButtonsPanel() {
        return workersPanel;
    }

    public void setLoginButtonsPanel(JPanel loginButtonsPanel) {
        this.workersPanel = loginButtonsPanel;
    }

    public JPanel getLoginPanel() {
        return adminPanel;
    }

    public void setLoginPanel(JPanel loginPanel) {
        this.adminPanel = loginPanel;
    }

    public JLabel getLoginTitleLabel() {
        return loginTitleLabel;
    }

    public void setLoginTitleLabel(JLabel loginTitleLabel) {
        this.loginTitleLabel = loginTitleLabel;
    }

    public JLabel getPassLabel() {
        return passLabel;
    }

    public void setPassLabel(JLabel passLabel) {
        this.passLabel = passLabel;
    }

    public JPasswordField getPassTextField() {
        return passTextField;
    }

    public void setPassTextField(JPasswordField passTextField) {
        this.passTextField = passTextField;
    }

    public JLabel getEnterPasswordLabel() {
        return enterPasswordLabel;
    }

    public void setEnterPasswordLabel(JLabel enterPasswordLabel) {
        this.enterPasswordLabel = enterPasswordLabel;
    }

    public JButton getLoginButton2() {
        return loginButton2;
    }

    public void setLoginButton2(JButton loginButton2) {
        this.loginButton2 = loginButton2;
    }

    public JPanel getPasswordButtonPanel() {
        return workerPasswordPanel;
    }

    public void setPasswordButtonPanel(JPanel passwordButtonPanel) {
        this.workerPasswordPanel = passwordButtonPanel;
    }

    public JPasswordField getPasswordButtonPanelTextField() {
        return passwordButtonPanelTextField;
    }

    public void setPasswordButtonPanelTextField(JPasswordField passwordButtonPanelTextField) {
        this.passwordButtonPanelTextField = passwordButtonPanelTextField;
    }

    public JButton getPasswordPanelBackButton() {
        return passwordPanelBackButton;
    }

    public void setPasswordPanelBackButton(JButton passwordPanelBackButton) {
        this.passwordPanelBackButton = passwordPanelBackButton;
    }

    public JLabel getWorkerNameLabel() {
        return workerNameLabel;
    }

    public void setWorkerNameLabel(JLabel workerNameLabel) {
        this.workerNameLabel = workerNameLabel;
    }

    private Intermediaria claseIntermedia = new Intermediaria(this);
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel adminBackgroundPanel;
    private javax.swing.JPanel adminPanel;
    private javax.swing.JButton adminPanelBackButton;
    private javax.swing.JButton adminSettingsButton;
    private javax.swing.JLabel emailLabel;
    private javax.swing.JTextField emailTextField;
    private javax.swing.JLabel enterPasswordLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JButton loginButton;
    private javax.swing.JButton loginButton2;
    private javax.swing.JLabel loginTitleLabel;
    private javax.swing.JLabel passLabel;
    private javax.swing.JPasswordField passTextField;
    private javax.swing.JPasswordField passwordButtonPanelTextField;
    private javax.swing.JButton passwordPanelBackButton;
    private javax.swing.JPanel workerBackgroundPanel;
    private javax.swing.JLabel workerNameLabel;
    private javax.swing.JPanel workerPasswordPanel;
    private javax.swing.JPanel workersPanel;
    // End of variables declaration//GEN-END:variables
}

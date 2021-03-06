
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author MuhammedAbdullah
 */
public class ClientFrame extends javax.swing.JFrame {

    /**
     * Creates new form ClientFrame
     */
    private Socket socket = null;
    private PrintStream out = null;
    private InputStreamReader isr = null;
    private BufferedReader br = null;

    public ClientFrame() {
        initComponents();
        eventList.setListData(new String[0]);
        sendBtn.setEnabled(false);
        commandField.setEnabled(false);
        disconnectBtn.setEnabled(false);
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        connectBtn = new javax.swing.JButton();
        sendBtn = new javax.swing.JButton();
        addressField = new javax.swing.JTextField();
        commandField = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        eventList = new javax.swing.JList();
        disconnectBtn = new javax.swing.JButton();
        stateLabel = new javax.swing.JLabel();
        serverAdressLabel = new javax.swing.JLabel();
        portLabel = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        commandLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        connectBtn.setText("Connect");
        connectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                connectBtnActionPerformed(evt);
            }
        });

        sendBtn.setText("Send");
        sendBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendBtnActionPerformed(evt);
            }
        });

        addressField.setText("127.0.0.1");

        commandField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                commandFieldActionPerformed(evt);
            }
        });

        eventList.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(eventList);

        disconnectBtn.setText("Disconnect");
        disconnectBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                disconnectBtnActionPerformed(evt);
            }
        });

        stateLabel.setForeground(new java.awt.Color(255, 51, 0));

        serverAdressLabel.setText("Server Adress:");

        portLabel.setText("Port:");

        jTextField1.setText("5000");

        commandLabel.setText("Command:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 900, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(57, 57, 57)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(stateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 234, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(serverAdressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(portLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(connectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 94, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(disconnectBtn)))
                .addGap(18, 18, 18))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(commandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 74, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(commandField, javax.swing.GroupLayout.PREFERRED_SIZE, 380, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(sendBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(217, 217, 217))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(stateLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addressField, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(connectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(disconnectBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(portLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(serverAdressLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(commandLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sendBtn, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(commandField, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void connectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_connectBtnActionPerformed

        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    String address = addressField.getText();
                    socket = new Socket(address, 5000);
                    appendToEventList("Connected to the Server - "+ address + ":" + 5000);
                    stateLabel.setText("Connected to the Server - " + address + ":" + 5000);
                    
                    out= new PrintStream(socket.getOutputStream());
                    isr= new InputStreamReader(socket.getInputStream());
                    br = new BufferedReader(isr);

                    disconnectBtn.setEnabled(true);
                    connectBtn.setEnabled(false);
                    sendBtn.setEnabled(true);
                    commandField.setEnabled(true);
                } catch (Exception ex) {
                    endConnection();
                }
            }
        });
    }//GEN-LAST:event_connectBtnActionPerformed

    private void commandFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_commandFieldActionPerformed

    }//GEN-LAST:event_commandFieldActionPerformed

    private void sendBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendBtnActionPerformed
        if(isConnectionClosed()){
            endConnection();
        }
        String command = commandField.getText();
        String username = getConnectedUsername();
        if(username != null)
            command = command + " by " + username;
        out.println(command+"<CR>");
        String str = null;
        try {
            str = br.readLine();
        } catch (IOException ex) {
            
        }
        appendToEventList(str);
        if(isConnectionClosed()){
            endConnection();
        }
    }//GEN-LAST:event_sendBtnActionPerformed

    private void disconnectBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_disconnectBtnActionPerformed
        try {
            out.println("quit");
            socket.close();
            stateLabel.setText("There is no connection");
            sendBtn.setEnabled(false);
            commandField.setEnabled(false);
            connectBtn.setEnabled(true);
            disconnectBtn.setEnabled(false);
        } catch (IOException ex) {
            Logger.getLogger(ClientFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_disconnectBtnActionPerformed

    public void appendToEventList(String str){
        String[] jlistData = new String[eventList.getModel().getSize() + 1];
        jlistData[0] = str;
        for (int i = 0; i < eventList.getModel().getSize(); i++) {
            jlistData[i + 1] = eventList.getModel().getElementAt(i).toString();
        }
        eventList.setListData(jlistData);
    }
    
    public boolean isConnectionClosed(){
        try{
            out.println("<CR>");
            br.readLine();
            return false;
        }
        catch(Exception e){}
        
        return true;
    }
    
    public void endConnection(){
        stateLabel.setText("There is no connection");
        sendBtn.setEnabled(false);
        commandField.setEnabled(false);
        connectBtn.setEnabled(true);
        disconnectBtn.setEnabled(false);
        commandField.setText("");
        try {
            if(socket != null && socket.isConnected())
                socket.close();
        } catch (Exception ex) {
        }
        appendToEventList("THERE IS NO CONNECTION");
    }
    
    public String getConnectedUsername(){
        String username = null;
        try{
            out.println("<CR>");
            username = br.readLine();
            
            if(username.startsWith("#")){
                username = username.substring(1,username.length());
                username = username.substring(0,username.lastIndexOf("#"));
            }
            else
                username = null;
        }
        catch(Exception exc){
            // if there is no connection, then exc will be thrown..
        }
        return username;
    }
    
    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField addressField;
    private javax.swing.JTextField commandField;
    private javax.swing.JLabel commandLabel;
    private javax.swing.JButton connectBtn;
    private javax.swing.JButton disconnectBtn;
    private javax.swing.JList eventList;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel portLabel;
    private javax.swing.JButton sendBtn;
    private javax.swing.JLabel serverAdressLabel;
    private javax.swing.JLabel stateLabel;
    // End of variables declaration//GEN-END:variables
}

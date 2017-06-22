/*
 * Client_FrontPage.java
 *
 * Created on April 6, 2008, 12:21 AM
 */

package Client;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;

/**
 *
 * @author  user12
 */
public class Client_FrontPage extends javax.swing.JFrame implements Runnable {
    
    Socket s=null;    
    DataInputStream dis=null;
    DataOutputStream dos=null;
    Thread t=new Thread(this);
    Hashtable MyRooms = new Hashtable();        // To handle the rooms with respect to the users of private chat....
    
    /** Creates new form Client_FrontPage */
    public Client_FrontPage() {
        initComponents();        
        this.setTitle("Sign In");
        this.setResizable(false);
        this.setDefaultLookAndFeelDecorated(false);
        SignInPanel.setVisible(true);           // To show inly SignIn panel...
        SignUpPanel.setVisible(false);
        ChatRootPanel.setVisible(false);
        
        this.setBounds(250,250,320,240);
        SignInPanel.setBounds(0,0,320,240);
        
        SocketConnection();
        
    }
        
    public void SocketConnection()
    {
        try
        {            
            s=new Socket("127.0.0.1",5534);              //172.16.35.42 used once
            dis=new DataInputStream(s.getInputStream());
            dos=new DataOutputStream(s.getOutputStream());
        }
        catch(Exception e)
        {
            e.printStackTrace();   
            return;
        }
        t.start();
    }
    public String createMessageType1()
    {
        String UName=UserNametxt.getText(),PWord=PassWordtxt.getText();;
        String Mesg="1|"+UName+"|"+PWord;        
        return Mesg;
    }
    
    public String createMessageType2()
    {
        String UName=NewUsertxt.getText(),PWord=NewPassWordtxt.getText(),Nick=NickNametxt.getText();
        String Mesg="2|"+UName+"|"+PWord+"|"+Nick;        
        return Mesg;
    }
    
    public void HandleMessageTypes(String msg)
    {
        String UN="",PW="",NN="",EM="";         // UN-UserName   PW-PassWord   NN-NickName   EM-ErrorMessage
        int UNL=0,PWL=0,NNL=0,EML=0;            // UNLength      PWLength      NNLength      EMLength     
        StringTokenizer st=new StringTokenizer(msg,"|");        
        System.out.println(msg.charAt(0));
        try
        {
            switch(Integer.parseInt(st.nextToken()))
            {
                case 1:                 // Reply to signin...
                {   
                    String tm[]=new String[3];
                    for(int i=0;st.hasMoreTokens();i++)
                    {
                        tm[i]=st.nextToken();
                    }
                    NN=tm[2];
                    Nicklbl.setText(NN);
                 
                    System.out.println("..................................."+NN+"..............................");
                    SignInPanel.setVisible(false);
                    ChatRootPanel.setVisible(true);
                    ChatRootPanel.setBounds(0,0,550,400);
                    this.setTitle("Chat Room - Public");
                    this.setBounds(250,250,550,400);
                    break;
                }
                    
                case 2:                 // Reply to Signup...
                {   
                    String tm[]=new String[3];
                    for(int i=0;st.hasMoreTokens();i++)
                    {
                        tm[i]=st.nextToken();
                    }
                    NN=tm[2];
                    Nicklbl.setText(NN);
                    
                    System.out.println("..................................."+NN+"..............................");
                    SignUpPanel.setVisible(false);
                    ChatRootPanel.setBounds(0,0,550,410);
                    ChatRootPanel.setVisible(true);
                    this.setTitle("Chat Room - Public");
                    this.setBounds(250,250,520,410);
                    break;
                }
                case 3:
                {
                    UsersList.removeAll();
                        
                    while(st.hasMoreTokens())
                    {
                        UsersList.add(st.nextToken());
                    }
                    break;
                }
                case 4:
                {
                    //Message for public chat room...
                    ChatRoomtxt.setText(ChatRoomtxt.getText()+st.nextToken()+"\n");
                    break;
                }
                case 5:
                {
                    //Message to open private room...                                       
                    String Target=st.nextToken();
                    
                    PrivateRoom pr=new PrivateRoom(Nicklbl.getText(),Target,s);
                    pr.setVisible(true);                   
                    
                    MyRooms.put(Target,pr);
                    
                    break;
                }
                case 6:
                {
                    //Message Received From server to Show the Private Room Messages...
                                        
                    String Target=st.nextToken();
                    String Msg=st.nextToken();
                    
                    PrivateRoom pr=(PrivateRoom)MyRooms.get(Target);
                    pr.UpdateMsgArea(Msg);
                    break;
                }
                case 7:
                    break;
                case 8:                         // The message to say that the pair going to close the window....
                {
                    String ToClose=st.nextToken();
                    PrivateRoom pr=(PrivateRoom)MyRooms.get(ToClose);
                    pr.CloseMessage();                   
                    pr.dispose();
                    break;
                }
                case 9:
                {
                    break;
                }
                case 10:
                {                    
                    String ReceiveFileFrom=st.nextToken();
                    String Filenm=st.nextToken();                 
                    PrivateRoom pr=(PrivateRoom)MyRooms.get(ReceiveFileFrom);
                    pr.CreatePlaceToSave(Filenm);                    
                    break;
                }
                case 11:
                {
                    
                    try
                    {         
                            String Flag=st.nextToken();
                            
                            if(Flag.equalsIgnoreCase("1"))
                            {   
                                String work=st.nextToken();         
                                String MsgFrom=st.nextToken();
                                PrivateRoom pr=(PrivateRoom)MyRooms.get(MsgFrom);
                                if(work.equalsIgnoreCase("0"))
                                {                                    
                                    pr.ReceiveFile();
                                }
                                else
                                {                                    
                                    pr.SendFile();
                                }
                                
                            }
                            else
                            {
                                String MsgFrom=st.nextToken();
                                PrivateRoom pr=(PrivateRoom)MyRooms.get(MsgFrom);
                                pr.NoWillMessage();                                
                            }
                            break;
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                }
                
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        SignInPanel = new javax.swing.JPanel();
        SignInlbl = new javax.swing.JLabel();
        UserNamelbl = new javax.swing.JLabel();
        Passwordlbl = new javax.swing.JLabel();
        UserNametxt = new javax.swing.JTextField();
        SignInbtn = new javax.swing.JButton();
        NewUserbtn = new javax.swing.JButton();
        PassWordtxt = new javax.swing.JPasswordField();
        SignUpPanel = new javax.swing.JPanel();
        SignUplbl = new javax.swing.JLabel();
        NewUserlbl = new javax.swing.JLabel();
        PassWordlbl = new javax.swing.JLabel();
        Registerbtn = new javax.swing.JButton();
        NewUsertxt = new javax.swing.JTextField();
        NickNamelbl = new javax.swing.JLabel();
        NickNametxt = new javax.swing.JTextField();
        NewPassWordtxt = new javax.swing.JPasswordField();
        ChatRootPanel = new javax.swing.JPanel();
        Nicklbl = new javax.swing.JLabel();
        MsgToSendtxt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        ChatRoomtxt = new javax.swing.JTextArea();
        UsersList = new java.awt.List();
        PublicRoomlbl = new javax.swing.JLabel();
        UsersOnLinelbl = new javax.swing.JLabel();
        smiles = new javax.swing.JLabel();
        smiles4 = new javax.swing.JLabel();
        smiles5 = new javax.swing.JLabel();
        smiles3 = new javax.swing.JLabel();
        smiles2 = new javax.swing.JLabel();

        getContentPane().setLayout(null);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        SignInPanel.setLayout(null);

        SignInPanel.setBackground(new java.awt.Color(0, 102, 102));
        SignInPanel.setBorder(new javax.swing.border.MatteBorder(null));
        SignInPanel.setForeground(new java.awt.Color(255, 255, 255));
        SignInlbl.setFont(new java.awt.Font("Broadway", 0, 18));
        SignInlbl.setForeground(new java.awt.Color(51, 204, 255));
        SignInlbl.setText("SIGN IN");
        SignInPanel.add(SignInlbl);
        SignInlbl.setBounds(120, 20, 80, 21);

        UserNamelbl.setFont(new java.awt.Font("Cambria", 0, 18));
        UserNamelbl.setForeground(new java.awt.Color(255, 255, 255));
        UserNamelbl.setText("UserName");
        SignInPanel.add(UserNamelbl);
        UserNamelbl.setBounds(40, 60, 90, 20);

        Passwordlbl.setFont(new java.awt.Font("Cambria", 0, 18));
        Passwordlbl.setForeground(new java.awt.Color(255, 255, 255));
        Passwordlbl.setText("PassWord");
        SignInPanel.add(Passwordlbl);
        Passwordlbl.setBounds(40, 100, 100, 20);

        UserNametxt.setFont(new java.awt.Font("Cambria", 0, 14));
        SignInPanel.add(UserNametxt);
        UserNametxt.setBounds(150, 60, 130, 24);

        SignInbtn.setFont(new java.awt.Font("Broadway", 0, 14));
        SignInbtn.setText("SIGN IN");
        SignInbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SignInbtnActionPerformed(evt);
            }
        });

        SignInPanel.add(SignInbtn);
        SignInbtn.setBounds(40, 160, 100, 25);

        NewUserbtn.setFont(new java.awt.Font("Broadway", 0, 14));
        NewUserbtn.setText("NEW USER");
        NewUserbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewUserbtnActionPerformed(evt);
            }
        });

        SignInPanel.add(NewUserbtn);
        NewUserbtn.setBounds(170, 160, 110, 25);

        PassWordtxt.setFont(new java.awt.Font("Cambria", 0, 14));
        SignInPanel.add(PassWordtxt);
        PassWordtxt.setBounds(150, 100, 130, 20);

        getContentPane().add(SignInPanel);
        SignInPanel.setBounds(660, 50, 320, 210);

        SignUpPanel.setLayout(null);

        SignUpPanel.setBackground(new java.awt.Color(0, 102, 102));
        SignUpPanel.setBorder(new javax.swing.border.MatteBorder(null));
        SignUplbl.setFont(new java.awt.Font("Broadway", 1, 18));
        SignUplbl.setForeground(new java.awt.Color(0, 204, 255));
        SignUplbl.setText("SIGN UP");
        SignUpPanel.add(SignUplbl);
        SignUplbl.setBounds(100, 10, 100, 30);

        NewUserlbl.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 14));
        NewUserlbl.setForeground(new java.awt.Color(255, 255, 255));
        NewUserlbl.setText("User Name");
        SignUpPanel.add(NewUserlbl);
        NewUserlbl.setBounds(20, 60, 90, 20);

        PassWordlbl.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 14));
        PassWordlbl.setForeground(new java.awt.Color(255, 255, 255));
        PassWordlbl.setText("Password");
        SignUpPanel.add(PassWordlbl);
        PassWordlbl.setBounds(20, 100, 90, 20);

        Registerbtn.setFont(new java.awt.Font("Broadway", 0, 14));
        Registerbtn.setText("REGISTER");
        Registerbtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegisterbtnActionPerformed(evt);
            }
        });

        SignUpPanel.add(Registerbtn);
        Registerbtn.setBounds(90, 190, 110, 25);

        NewUsertxt.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 14));
        SignUpPanel.add(NewUsertxt);
        NewUsertxt.setBounds(140, 60, 120, 20);

        NickNamelbl.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 14));
        NickNamelbl.setForeground(new java.awt.Color(255, 255, 255));
        NickNamelbl.setText("Nick Name");
        SignUpPanel.add(NickNamelbl);
        NickNamelbl.setBounds(20, 140, 90, 20);

        NickNametxt.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 14));
        SignUpPanel.add(NickNametxt);
        NickNametxt.setBounds(140, 140, 120, 20);

        NewPassWordtxt.setFont(new java.awt.Font("Copperplate Gothic Bold", 0, 14));
        SignUpPanel.add(NewPassWordtxt);
        NewPassWordtxt.setBounds(140, 100, 120, 23);

        getContentPane().add(SignUpPanel);
        SignUpPanel.setBounds(660, 280, 290, 240);

        ChatRootPanel.setLayout(null);

        ChatRootPanel.setBackground(new java.awt.Color(102, 102, 0));
        ChatRootPanel.setBorder(new javax.swing.border.MatteBorder(null));
        Nicklbl.setFont(new java.awt.Font("Cambria", 0, 14));
        Nicklbl.setForeground(new java.awt.Color(255, 255, 255));
        Nicklbl.setText("Nick Name");
        ChatRootPanel.add(Nicklbl);
        Nicklbl.setBounds(80, 50, 70, 20);

        MsgToSendtxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                MsgToSendtxtKeyPressed(evt);
            }
        });

        ChatRootPanel.add(MsgToSendtxt);
        MsgToSendtxt.setBounds(80, 290, 380, 20);

        ChatRoomtxt.setColumns(20);
        ChatRoomtxt.setLineWrap(true);
        ChatRoomtxt.setRows(5);
        jScrollPane1.setViewportView(ChatRoomtxt);

        ChatRootPanel.add(jScrollPane1);
        jScrollPane1.setBounds(80, 80, 270, 210);

        UsersList.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsersListActionPerformed(evt);
            }
        });

        ChatRootPanel.add(UsersList);
        UsersList.setBounds(350, 80, 110, 210);

        PublicRoomlbl.setFont(new java.awt.Font("Cambria", 0, 14));
        PublicRoomlbl.setForeground(new java.awt.Color(255, 255, 255));
        PublicRoomlbl.setText("Public Room");
        ChatRootPanel.add(PublicRoomlbl);
        PublicRoomlbl.setBounds(270, 50, 80, 20);

        UsersOnLinelbl.setFont(new java.awt.Font("Cambria", 0, 14));
        UsersOnLinelbl.setForeground(new java.awt.Color(255, 255, 255));
        UsersOnLinelbl.setText("Users OnLine");
        ChatRootPanel.add(UsersOnLinelbl);
        UsersOnLinelbl.setBounds(360, 50, 90, 18);

        smiles.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/cool.png")));
        ChatRootPanel.add(smiles);
        smiles.setBounds(-10, 290, 150, 140);

        smiles4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/surprise.png")));
        ChatRootPanel.add(smiles4);
        smiles4.setBounds(210, 280, 120, 140);

        smiles5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/angel_1.png")));
        ChatRootPanel.add(smiles5);
        smiles5.setBounds(0, 0, 128, 130);

        smiles3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/angel.png")));
        ChatRootPanel.add(smiles3);
        smiles3.setBounds(430, 0, 130, 130);

        smiles2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/Client/hmm.png")));
        ChatRootPanel.add(smiles2);
        smiles2.setBounds(460, 300, 130, 130);

        getContentPane().add(ChatRootPanel);
        ChatRootPanel.setBounds(50, 60, 550, 400);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void UsersListActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsersListActionPerformed
        String DestUser=UsersList.getSelectedItem(),Sender=Nicklbl.getText();
        
        String Msg="5|"+Sender+"|"+DestUser;
        try
        {
            dos.writeUTF(Msg);
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        
    }//GEN-LAST:event_UsersListActionPerformed

    private void MsgToSendtxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_MsgToSendtxtKeyPressed
        if(evt.getKeyText(evt.getKeyCode()).equalsIgnoreCase("Enter"))
        {
            try
            {
                dos.writeUTF("4|"+MsgToSendtxt.getText());
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_MsgToSendtxtKeyPressed

    private void NewUserbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewUserbtnActionPerformed
        SignUpPanel.setVisible(true);
        SignUpPanel.setBounds(0,0,300,250);
        this.setTitle("Sign Up");
        this.setBounds(250,250,300,250);
        SignInPanel.setVisible(false);
    }//GEN-LAST:event_NewUserbtnActionPerformed

    private void SignInbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SignInbtnActionPerformed
         try
        {
            String msg=createMessageType1();
            dos.writeUTF(msg);            
        }
        catch(Exception e)
        {
            e.printStackTrace();
            System.out.println(e.toString());
        }
    }//GEN-LAST:event_SignInbtnActionPerformed

    private void RegisterbtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegisterbtnActionPerformed
        try
        {
            dos.writeUTF(createMessageType2());
            //SignUp Messasge Sent....
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_RegisterbtnActionPerformed
    
    
    public void run()
    {
        while(true)
        {
            try
            {                
                String str=dis.readUTF();                
                HandleMessageTypes(str);                
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Client_FrontPage().setVisible(true);
            }
        });
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea ChatRoomtxt;
    private javax.swing.JPanel ChatRootPanel;
    private javax.swing.JTextField MsgToSendtxt;
    private javax.swing.JPasswordField NewPassWordtxt;
    private javax.swing.JButton NewUserbtn;
    private javax.swing.JLabel NewUserlbl;
    private javax.swing.JTextField NewUsertxt;
    private javax.swing.JLabel NickNamelbl;
    private javax.swing.JTextField NickNametxt;
    private javax.swing.JLabel Nicklbl;
    private javax.swing.JLabel PassWordlbl;
    private javax.swing.JPasswordField PassWordtxt;
    private javax.swing.JLabel Passwordlbl;
    private javax.swing.JLabel PublicRoomlbl;
    private javax.swing.JButton Registerbtn;
    private javax.swing.JPanel SignInPanel;
    private javax.swing.JButton SignInbtn;
    private javax.swing.JLabel SignInlbl;
    private javax.swing.JPanel SignUpPanel;
    private javax.swing.JLabel SignUplbl;
    private javax.swing.JLabel UserNamelbl;
    private javax.swing.JTextField UserNametxt;
    private java.awt.List UsersList;
    private javax.swing.JLabel UsersOnLinelbl;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel smiles;
    private javax.swing.JLabel smiles2;
    private javax.swing.JLabel smiles3;
    private javax.swing.JLabel smiles4;
    private javax.swing.JLabel smiles5;
    // End of variables declaration//GEN-END:variables
    
}

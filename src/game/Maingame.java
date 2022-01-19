package game;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Timer;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiSystem;
import javax.sound.midi.MidiUnavailableException;
import javax.sound.midi.Sequence;
import javax.sound.midi.Sequencer;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.ScrollPaneConstants;

public class Maingame{
	JFrame game;
	Background background = new Background(0, 0, "image/Background/sea.jpg");
	Base imgFailed = new Base(400, 300, "image/Extra/gameover.gif", false);
	final int width = 960;
	final int height = 720;
	int[] score = new int[1];
	Label scoreLabel = new Label(830, 20, 100, 40);
	String player1, player2, bullet1, bullet2;
    ArrayList<Playercraft> playerList = new ArrayList<Playercraft>();
    ArrayList<Enemycraft> enemyList = new ArrayList<Enemycraft>();
    ArrayList<Enemycraft> enemyDelList = new ArrayList<Enemycraft>();
    ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
    ArrayList<Supply> supplyList = new ArrayList<Supply>();
    ArrayList<Supply> supplyDelList = new ArrayList<Supply>();
    //int gameStatus = 0;
    int playerMode = 1;
    int gameVoice = 10;
    boolean pause = false;
    Timer timer;
    JLabel save;
    Random random = new Random();
    Map<String, Boolean> aircraftBonus;
    gameCanvas canvas = new gameCanvas();
    
    public Maingame() {
    	game = new JFrame();
    	game.setTitle("Sky Combat");    //窗口的标题，this.指MyGameFrame类的
        game.setSize(width, height);    //设置窗口显示的大小，这里把窗口的大小写在一个常量类中，通过调用避免了多处修改的麻烦，方便了维护和修改！
        game.setLocation(100, 40);    //设置窗口出现的位置
        game.setLayout(null);
        game.setVisible(true);    //设置让窗口显示出来
        new BackgroundMusic();
    }
    
    public void initGame() {
    	imgFailed = new Base(400, 300, "image/Extra/gameover.gif", false);
    	score = new int[1];
        playerList = new ArrayList<Playercraft>();
        enemyList = new ArrayList<Enemycraft>();
        enemyDelList = new ArrayList<Enemycraft>();
        bulletList = new ArrayList<Bullet>();
        supplyList = new ArrayList<Supply>();
        supplyDelList = new ArrayList<Supply>();
        pause = false;
    }
    
    public void launch() {
        MenuBar menuBar = new MenuBar(); 
		
		Menu menuGame = new Menu("Game");
		menuBar.add(menuGame);
		MenuItem game1= new MenuItem("New");
		game1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				selectPanel();
			}
		});
		MenuItem game2 = new MenuItem("Save");
		game2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				saveGame();
			}
		});
		MenuItem game3 = new MenuItem("Load");
		game3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				loadPanel();
			}
		});
		menuGame.add(game1);
		menuGame.add(game2);
		menuGame.add(game3);
		
		Menu menuHelp = new Menu("Help");
		menuBar.add(menuHelp);
		MenuItem help1 = new MenuItem("About");
		help1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showAboutDialog();
			}
		});
		menuHelp.add(help1);
		
		game.setMenuBar(menuBar);
		
		initGame();
        JPanel panelStart = new JPanel();
        panelStart.setLayout(null);
        
        JLabel labelTitle = new JLabel("Sky Combat");
        labelTitle.setFont(new Font("Times", Font.BOLD, 72));
        labelTitle.setBounds(width / 2 - 220, 180, 460, 100);
        JButton btnContinue = new JButton("继续游戏");
        JButton btnNew = new JButton("新游戏");
        JButton btnSetting = new JButton("游戏设置");
        JButton btnExit = new JButton("退出游戏");
        btnContinue.setBounds(width / 2 - 80, 330, 160, 60);
        btnNew.setBounds(width / 2 - 80, 410, 160, 60);
        btnSetting.setBounds(width / 2 - 80, 490, 160, 60);
        btnExit.setBounds(width / 2 - 80, 570, 160, 60);
        
        panelStart.add(labelTitle);
    	panelStart.add(btnContinue);
    	panelStart.add(btnNew);
    	panelStart.add(btnSetting);
    	panelStart.add(btnExit);
    	//this.add(panelStart);
    	panelStart.setBounds(0, 0, width, height);
    	game.setContentPane(panelStart);
    	
        JButton btnSingle = new JButton("单人游戏");
        JButton btnDouble = new JButton("双人游戏");
        btnSingle.setBounds(width / 2 + 90, 410, 100, 30);
        btnDouble.setBounds(width / 2 + 90, 450, 100, 30);
        btnSingle.setVisible(false);
        btnDouble.setVisible(false);
        panelStart.add(btnSingle);
        panelStart.add(btnDouble);
        
        btnNew.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				btnSingle.setVisible(true);
				btnDouble.setVisible(true);
			}
        });
        
        btnContinue.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				loadPanel();
			}
        });
        
        btnSetting.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				settingPanel();
			}
        });
        
        btnExit.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
        });
        
        btnSingle.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				playerMode = 1;
				selectPanel();
			}
        });
        
        btnDouble.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				playerMode = 2;
				selectPanel();
			}
        });
        
        /*TimerTask task = new TimerTask(){
        	public void run(){
        		new PaintThread().start(); 
        	}
		};
		timer = new Timer();
		timer.schedule(task, 0, 100);*/
        
		game.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
    
    public void selectPanel() {
    	//this.remove(panelStart);
    	JPanel panelSelect = new JPanel();
        panelSelect.setLayout(new BorderLayout());
    	
        if(playerMode == 1) {
        	JPanel panelPlayer = new JPanel();
            panelPlayer.setPreferredSize(new Dimension(1000, 1000));
            //panelPlayer.setBackground(Color.WHITE);
            panelPlayer.setLayout(null);
            
            JLabel labelPlane = new JLabel("请选择飞机：");
            labelPlane.setFont(new Font("Times", Font.BOLD, 24));
            labelPlane.setBounds(200, 100, 200, 25);
            panelPlayer.add(labelPlane);
            
            JComboBox<String> comboPlane = new JComboBox<String>();
            comboPlane.setBounds(200, 130, 200, 20);
            comboPlane.addItem("----请选择----");
			for(int i = 1; i <= 10; i++) {
				comboPlane.addItem("class" + i);
			}
			
			JLabel imgPlane1 = new JLabel();
			imgPlane1.setBounds(250, 200, 100, 80);
            panelPlayer.add(imgPlane1);
            
            JLabel imgPlane2 = new JLabel();
			imgPlane2.setBounds(250, 300, 100, 80);
            panelPlayer.add(imgPlane2);
            
            JLabel imgPlane3 = new JLabel();
			imgPlane3.setBounds(250, 400, 100, 80);
            panelPlayer.add(imgPlane3);
            
			comboPlane.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					player1 = (String)comboPlane.getSelectedItem();
					imgPlane1.setIcon(new ImageIcon("image/Player/" + player1 + "/1.gif"));
					imgPlane2.setIcon(new ImageIcon("image/Player/" + player1 + "/2.gif"));
					imgPlane3.setIcon(new ImageIcon("image/Player/" + player1 + "/3.gif"));
				}
			});
			panelPlayer.add(comboPlane);
			
			JLabel labelBullet = new JLabel("请选择炮弹：");
            labelBullet.setFont(new Font("Times", Font.BOLD, 24));
            labelBullet.setBounds(500, 100, 200, 25);
            panelPlayer.add(labelBullet);
            
			JComboBox<String> comboBullet = new JComboBox<String>();
			comboBullet.setBounds(500, 130, 200, 20);
			comboBullet.addItem("----请选择----");
			for(int i = 1; i <= 3; i++) {
				comboBullet.addItem("class" + i);
			}
			
			JLabel imgBullet = new JLabel();
			imgBullet.setBounds(550, 300, 80, 80);
            panelPlayer.add(imgBullet);
            
			comboBullet.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					bullet1 = (String)comboBullet.getSelectedItem();
					imgBullet.setIcon(new ImageIcon("image/Bullet/" + bullet1.charAt(5) + ".png"));
				}
			});
			panelPlayer.add(comboBullet);
			
			JLabel labelIllustrate = new JLabel("<html><body>操作说明：<br>W：前进<br>S：后退<br>A：左移<br>D：右移<br>SPACE：开火<body></html>");
			labelIllustrate.setBounds(100, 450, 200, 200);
			labelIllustrate.setFont(new Font("Times", Font.BOLD, 12));
			panelPlayer.add(labelIllustrate);
			
    	    panelSelect.add(panelPlayer, BorderLayout.CENTER);
        }
        else {
        	JPanel panelPlayer1 = new JPanel();
        	panelPlayer1.setPreferredSize(new Dimension(480, 700));
        	//panelPlayer1.setBackground(Color.WHITE);
        	panelPlayer1.setLayout(null);
        	
        	JLabel labelPlayer1 = new JLabel("1号玩家：");
            labelPlayer1.setFont(new Font("Times", Font.BOLD, 20));
            labelPlayer1.setBounds(50, 60, 100, 20);
            panelPlayer1.add(labelPlayer1);
            
        	JLabel labelPlane1 = new JLabel("请选择飞机：");
            labelPlane1.setFont(new Font("Times", Font.BOLD, 16));
            labelPlane1.setBounds(50, 100, 100, 25);
            panelPlayer1.add(labelPlane1);
            
        	JComboBox<String> comboPlane1 = new JComboBox<String>();
        	comboPlane1.setBounds(50, 130, 150, 20);
            comboPlane1.addItem("----请选择----");
            for(int i = 1; i <= 10; i++) {
				comboPlane1.addItem("class" + i);
			}
            
            JLabel imgPlane1 = new JLabel();
			imgPlane1.setBounds(75, 200, 100, 80);
            panelPlayer1.add(imgPlane1);
            
            JLabel imgPlane2 = new JLabel();
			imgPlane2.setBounds(75, 300, 100, 80);
            panelPlayer1.add(imgPlane2);
            
            JLabel imgPlane3 = new JLabel();
			imgPlane3.setBounds(75, 400, 100, 80);
            panelPlayer1.add(imgPlane3);
            
            comboPlane1.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					player1 = (String)comboPlane1.getSelectedItem();
					imgPlane1.setIcon(new ImageIcon("image/Player/" + player1 + "/1.gif"));
					imgPlane2.setIcon(new ImageIcon("image/Player/" + player1 + "/2.gif"));
					imgPlane3.setIcon(new ImageIcon("image/Player/" + player1 + "/3.gif"));
				}
			});
			panelPlayer1.add(comboPlane1);
			
			JLabel labelBullet1 = new JLabel("请选择炮弹：");
            labelBullet1.setFont(new Font("Times", Font.BOLD, 16));
            labelBullet1.setBounds(250, 100, 100, 25);
            panelPlayer1.add(labelBullet1);
            
			JComboBox<String> comboBullet1 = new JComboBox<String>();
			comboBullet1.setBounds(250, 130, 150, 20);
			comboBullet1.addItem("----请选择----");
			for(int i = 1; i <= 3; i++) {
				comboBullet1.addItem("class" + i);
			}
			
			JLabel imgBullet1 = new JLabel();
			imgBullet1.setBounds(300, 300, 80, 80);
            panelPlayer1.add(imgBullet1);
            
			comboBullet1.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					bullet1 = (String)comboBullet1.getSelectedItem();
					imgBullet1.setIcon(new ImageIcon("image/Bullet/" + bullet1.charAt(5) + ".png"));
				}
			});
			panelPlayer1.add(comboBullet1);
			
			JLabel labelIllustrate1 = new JLabel("<html><body>操作说明：<br>W：前进<br>S：后退<br>A：左移<br>D：右移<br>SPACE：开火<body></html>");
			labelIllustrate1.setBounds(50, 450, 200, 200);
			labelIllustrate1.setFont(new Font("Times", Font.BOLD, 12));
			panelPlayer1.add(labelIllustrate1);
			
    	    panelSelect.add(panelPlayer1, BorderLayout.WEST);
    	    
        	JPanel panelPlayer2 = new JPanel();
        	panelPlayer2.setPreferredSize(new Dimension(480, 700));
        	//panelPlayer2.setBackground(Color.WHITE);
        	panelPlayer2.setLayout(null);
        	
        	JLabel labelPlayer2 = new JLabel("2号玩家：");
            labelPlayer2.setFont(new Font("Times", Font.BOLD, 20));
            labelPlayer2.setBounds(50, 60, 100, 20);
            panelPlayer2.add(labelPlayer2);
            
        	JLabel labelPlane2 = new JLabel("请选择飞机：");
            labelPlane2.setFont(new Font("Times", Font.BOLD, 16));
            labelPlane2.setBounds(50, 100, 100, 20);
            panelPlayer2.add(labelPlane2);
            
        	JComboBox<String> comboPlane2 = new JComboBox<String>();
        	comboPlane2.setBounds(50, 130, 150, 20);
            comboPlane2.addItem("----请选择----");
            for(int i = 1; i <= 10; i++) {
				comboPlane2.addItem("class" + i);
			}
            
            JLabel imgPlane4 = new JLabel();
			imgPlane4.setBounds(75, 200, 100, 80);
            panelPlayer2.add(imgPlane4);
            
            JLabel imgPlane5 = new JLabel();
			imgPlane5.setBounds(75, 300, 100, 80);
            panelPlayer2.add(imgPlane5);
            
            JLabel imgPlane6 = new JLabel();
			imgPlane6.setBounds(75, 400, 100, 80);
            panelPlayer2.add(imgPlane6);
            
            comboPlane2.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					player2 = (String)comboPlane2.getSelectedItem();
					imgPlane4.setIcon(new ImageIcon("image/Player/" + player2 + "/1.gif"));
					imgPlane5.setIcon(new ImageIcon("image/Player/" + player2 + "/2.gif"));
					imgPlane6.setIcon(new ImageIcon("image/Player/" + player2 + "/3.gif"));
				}
			});
			panelPlayer2.add(comboPlane2);
			
			JLabel labelBullet2 = new JLabel("请选择炮弹：");
            labelBullet2.setFont(new Font("Times", Font.BOLD, 16));
            labelBullet2.setBounds(250, 100, 100, 20);
            panelPlayer2.add(labelBullet2);
            
			JComboBox<String> comboBullet2 = new JComboBox<String>();
			comboBullet2.setBounds(250, 130, 150, 20);
			comboBullet2.addItem("----请选择----");
			for(int i = 1; i <= 3; i++) {
				comboBullet2.addItem("class" + i);
			}
			
			JLabel imgBullet2 = new JLabel();
			imgBullet2.setBounds(300, 300, 80, 80);
            panelPlayer2.add(imgBullet2);
            
			comboBullet2.addItemListener(new ItemListener() {
				@Override
				public void itemStateChanged(ItemEvent e) {
					bullet2 = (String)comboBullet2.getSelectedItem();
					imgBullet2.setIcon(new ImageIcon("image/Bullet/" + bullet2.charAt(5) + ".png"));
				}
			});
			panelPlayer2.add(comboBullet2);
			
			JLabel labelIllustrate2 = new JLabel("<html><body>操作说明：<br>方向键上：前进<br>方向键下：后退<br>方向键左：左移<br>方向键右：右移<br>ENTER：开火<body></html>");
			labelIllustrate2.setBounds(50, 450, 200, 200);
			labelIllustrate2.setFont(new Font("Times", Font.BOLD, 12));
			panelPlayer2.add(labelIllustrate2);
			
    	    panelSelect.add(panelPlayer2, BorderLayout.EAST);
        }
        
        JPanel panelButton = new JPanel();
        JButton btnConfirm = new JButton("开始游戏");
        btnConfirm.setBounds(width / 2 - 80, 360, 160, 60);
        btnConfirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				if(playerMode == 1) {
					playerList.add(new Playercraft(game, 250, 450, player1, 100, 0, "image/Bullet/" + bullet1.charAt(5) + ".png"));
				}
				else {
					playerList.add(new Playercraft(game, 250, 450, player1, 100, 0, "image/Bullet/" + bullet1.charAt(5) + ".png"));
					playerList.add(new Playercraft(game, 650, 450, player2, 100, 1, "image/Bullet/" + bullet2.charAt(5) + ".png"));
				}
				mainPanel();
			}
        });
        JButton btnBack = new JButton("返回");
        btnBack.setBounds(width / 2 - 80, 360, 160, 60);
        btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				launch();
			}
        });
        
        panelButton.add(btnConfirm);
        panelButton.add(btnBack);
        
        panelButton.setPreferredSize(new Dimension(100, 40));
        panelSelect.add(panelButton, BorderLayout.SOUTH);
        game.setContentPane(panelSelect);
        game.repaint();
    	game.revalidate();
    }
    
    public void settingPanel() {
    	//this.remove(panelStart);
    	JPanel panelSetting = new JPanel();
        panelSetting.setLayout(null);
    	
        JLabel labelVoice = new JLabel("音量设置：");
        labelVoice.setFont(new Font("Times", Font.BOLD, 24));
        labelVoice.setBounds(100, 100, 200, 25);
        panelSetting.add(labelVoice);
        
        JSlider slideVoice = new JSlider(0, 10, 10);
        slideVoice.setBounds(250, 100, 600, 50);
        slideVoice.setMajorTickSpacing(5);
        slideVoice.setMinorTickSpacing(1);
        slideVoice.setPaintTicks(true);
        slideVoice.setPaintLabels(true);
        panelSetting.add(slideVoice);
        
        JButton btnConfirm = new JButton("确认");
        btnConfirm.setBounds(width / 2 - 200, 600, 160, 30);
        btnConfirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				gameVoice = slideVoice.getValue();
				launch();
			}
        });
        JButton btnCancel = new JButton("取消");
        btnCancel.setBounds(width / 2 + 40, 600, 160, 30);
        btnCancel.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				launch();
			}
        });
        
        panelSetting.add(btnConfirm);
        panelSetting.add(btnCancel);
        
        game.setContentPane(panelSetting);
        game.repaint();
    	game.revalidate();
    }
    
    public void loadPanel() {
    	JPanel panelLoad = new JPanel();
        panelLoad.setLayout(new BorderLayout());
        
    	JPanel panelSave = new JPanel();
        panelSave.setPreferredSize(new Dimension(1000, 1000));
        panelSave.setLayout(null);
        
        ArrayList<JLabel> saveList = getSaveInfo();
        for(JLabel item: saveList) {
        	panelSave.add(item);
        }
        
        JScrollPane scrollPane = new JScrollPane(panelSave, ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS, ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
	    scrollPane.setPreferredSize(new Dimension(600, 600));
	    panelLoad.add(scrollPane, BorderLayout.CENTER);
        
        JPanel panelButton = new JPanel();
        JButton btnConfirm = new JButton("开始游戏");
        btnConfirm.setBounds(width / 2 - 80, 360, 160, 60);
        btnConfirm.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				String id = save.getText().split("<br>")[2].replace("<body></html>", "");
				SaveFile savefile = SaveFile.load(id);
				score = savefile.score;
				playerList = savefile.playerList;
				enemyList = savefile.enemyList;
				enemyDelList = savefile.enemyDelList;
				bulletList = savefile.bulletList;
				supplyList = savefile.supplyList;
				playerMode = savefile.playerMode;
				aircraftBonus = savefile.aircraftBonus;
				mainPanel();
			}
        });
        JButton btnBack = new JButton("返回");
        btnBack.setBounds(width / 2 - 80, 360, 160, 60);
        btnBack.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				launch();
			}
        });
        
        panelButton.add(btnConfirm);
        panelButton.add(btnBack);
        
        panelButton.setPreferredSize(new Dimension(100, 40));
        panelLoad.add(panelButton, BorderLayout.SOUTH);
        game.setContentPane(panelLoad);
        game.repaint();
    	game.revalidate();
    }
    
    public ArrayList<JLabel> getSaveInfo() {
    	ArrayList<JLabel> saveList = new ArrayList<JLabel>();
    	File folder = new File("savefile");
    	File[] fileList = folder.listFiles();
    	
    	for(int i = 0; i < fileList.length; i++) {
    		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE MMM dd HH:mm:ss yyyy Z", Locale.ENGLISH);
            Date date = new Date(Long.parseLong(fileList[i].getName()));
            String time = simpleDateFormat.format(date);
            String content = "<html><body>";
            content += "存档名:存档" + i + "<br>";	
        	content += "保存时间:" + time + "<br>";
        	content += fileList[i].getName();
        	content += "<body></html>";
    		JLabel saveFile = new JLabel(content);
    		saveFile.setBounds(70, 50 + 150 * i, 800, 100);
    		saveFile.setOpaque(true);
    		saveFile.setBackground(Color.WHITE);
    		saveFile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
    		saveFile.addMouseListener(new MouseListener() {
				@Override
				public void mouseClicked(MouseEvent e) {
					for(JLabel item: saveList) {
						item.setBackground(Color.WHITE);
			        }
					saveFile.setBackground(Color.GRAY);
					save = saveFile;
				}

				@Override
				public void mousePressed(MouseEvent e) {
					
				}

				@Override
				public void mouseReleased(MouseEvent e) {
					
				}

				@Override
				public void mouseEntered(MouseEvent e) {
					saveFile.setBackground(Color.GRAY);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					if(save != saveFile) {
						saveFile.setBackground(Color.WHITE);
					}
				}
    		});
    		saveList.add(saveFile);
    	}
		return saveList;
    }
    
    public void saveGame() {
    	SaveFile save = new SaveFile(this);
    	save.save();
    }
    
    public void mainPanel() {
    	//this.remove(panelStart);
    	JPanel panelGame = new JPanel();
        panelGame.setLayout(new BorderLayout());
    	canvas.setPreferredSize(new Dimension(width, height - 20));
    	
    	panelGame.add(canvas, BorderLayout.CENTER);
    	panelGame.setPreferredSize(new Dimension(width, height));
    	game.add(panelGame);
    	game.setContentPane(panelGame);
    	game.addKeyListener(new KeyMonitor());
    	game.requestFocusInWindow();
    	game.repaint();
    	game.revalidate();
    	new PaintThread().start();
    }
    
    private void showAboutDialog() {
		JDialog aboutDialog = new JDialog(game, "About", true);
		aboutDialog.setSize(400, 200);
		aboutDialog.setResizable(false);
		//aboutDialog.setLocation(400, 400);
		aboutDialog.setLocationRelativeTo(null);
		JPanel panel = new JPanel();
		panel.setLayout(null);
		
		JLabel label1 = new JLabel("<html><body>Sky Combat<br>Version: 0.5.1<body></html>");
		label1.setBounds(20, 10, 300, 64);
		label1.setFont(new Font("Times", Font.BOLD, 20));
		
		JLabel label2 = new JLabel("Teammate:LCJ/FZY/LYX/TYZ/WK");
		label2.setBounds(20, 80, 300, 30);
		
		JLabel label3 = new JLabel("Powered by FZY");
		label3.setBounds(20, 120, 300, 30);
        
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        
        JButton btnExit = new JButton("Exit");
        btnExit.setBounds(250, 120, 100, 30);
        
        btnExit.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				aboutDialog.dispose();
			}
        });
        
        panel.add(btnExit);
        
        aboutDialog.setContentPane(panel);
        aboutDialog.setVisible(true);
	}
    
    public static void main(String[] args) {
        Maingame game = new Maingame();
        game.launch();
        //game.selectPanel();
        //game.mainPanel();
    }
    
    private void sumBullet(ArrayList<Playercraft> playerList, ArrayList<Enemycraft> enemyList) {
    	for(Playercraft player: playerList) {
    		if(!player.bulletList.isEmpty()) {
    			bulletList.addAll(player.bulletList);
    		}
    	}
    	for(Enemycraft enemy: enemyList) {
    		if(!enemy.bulletList.isEmpty()) {
    			bulletList.addAll(enemy.bulletList);
    		}
    	}
    }
    
    private void createEnemy() {
    	if(enemyList.size() < 15) {
    		if(random.nextInt(100) < 5) {
    			enemyList.add(new Enemycraft(game, random.nextInt(width), -random.nextInt(500), "image/Enemy/3.gif", 5, true));
    		}
    		else {
    			enemyList.add(new Enemycraft(game, random.nextInt(width), -random.nextInt(500), "image/Enemy/1.gif", 1));
    		}
    		enemyList.add(new Obstacle(game, random.nextInt(width), -random.nextInt(500), "image/Enemy/stone.gif", 20));
    	}
    	if(score[0] % 100 == 0 && score[0] > 0) {
    		score[0]++;
    		enemyList.add(new Bosscraft(game, width / 2 - 40, -random.nextInt(500), "image/Enemy/2.gif", 10));
    	}
    }
    
    private void createSupply() {
    	if(supplyList.size() < 2) {
    		if(random.nextInt(90) < 30) {
    			supplyList.add(new Supply(random.nextInt(width), -random.nextInt(500), "image/Supply/lives.gif", "hp"));
    		}
    		else if(random.nextInt(90) < 60) {
    			supplyList.add(new Supply(random.nextInt(width), -random.nextInt(500), "image/Supply/box1.gif", "bullet"));
    		}
    		else {
    			supplyList.add(new Supply(random.nextInt(width), -random.nextInt(500), "image/Supply/oil.gif", "oil"));
    		}
    	}
    }
    
    private class gameCanvas extends Canvas{
		private static final long serialVersionUID = 1L;

		@Override
	    public void paint(Graphics brush) {
			background.display(brush);
			createEnemy();
			createSupply();
	        for(Playercraft player: playerList) {
	        	player.move();
		        player.display(brush, bulletList, supplyList, enemyList);
		        if(player.hp <= 0) {
		        	pause = true;
		        	imgFailed.visible = true;
		        }
	        }
	        for(Enemycraft enemy: enemyList) {
	        	enemy.posCheck(width, height, enemyDelList);
		        enemy.move();
		        enemy.display(brush, score, bulletList, playerList, enemyDelList, aircraftBonus);
	        }
	        for(Enemycraft enemy: enemyDelList) {
	        	enemyList.remove(enemy);
	        }
	        enemyDelList.clear();
	        
	        for(Supply supply: supplyList) {
	        	if(!supply.judge(height)) {
	    			supplyDelList.add(supply);
	    		}
	        	else {
	        		supply.move();
	        		supply.display(brush);
	        	}
	        }
	    	supplyList.removeAll(supplyDelList);
	        supplyDelList.clear();
	        
	        sumBullet(playerList, enemyList);
	        for(Bullet item: bulletList) {
	        	item.move();
	        	item.display(brush);
	        }
	        scoreLabel.display(brush, "Score:" + score[0], 24);
	        imgFailed.display(brush);
	    }
		
		private Image offScreenImage = null;
		@Override
	    public void update(Graphics g) {
	        if(offScreenImage == null)
	            offScreenImage = this.createImage(960, 720);

	        Graphics gOff = offScreenImage.getGraphics();
	        paint(gOff);
	        g.drawImage(offScreenImage, 0, 0, null);
	    }
    }
	
    class PaintThread extends Thread{
        @Override
        public void run() {
        	while(true) {
	        	while(pause) {
	            	try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
	            }
                canvas.repaint();
                try {
                    Thread.sleep(40);  //游戏画质：40帧
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
        	}
        }
    }
    
    class BackgroundMusic implements Runnable{
    	private Sequencer midi;
        private String[] names = {"music/Joyful Life.mid","music/Inner Peace.mid","music/Einswei.mid"};
        private Map<String,Sequence> map;
        
        public BackgroundMusic(){
        	System.setProperty("javax.sound.midi.Sequencer","com.sun.media.sound.RealTimeSequencerProvider");
            initMap();
            new Thread(this).start();
        }
        
        private void initMap(){
                map = new Hashtable<String, Sequence>();
                try {
					midi = MidiSystem.getSequencer();
					midi.open();
	                for (String s: names) {
                        Sequence s1;
						try {
							s1 = MidiSystem.getSequence(new File(s));
							map.put(s, s1);
						} catch (InvalidMidiDataException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
	                }
				} catch (MidiUnavailableException e) {
					e.printStackTrace();
				}
        }
        
        private void createPlayer(String name){
            try {
                Sequence se = map.get(name);

                midi.setSequence(se);
                midi.start();
            }catch (InvalidMidiDataException e) {
            	e.printStackTrace();
            }
            
        }
        
        public void run(){
            while(true){
                try {
                    String name = names[(int)(Math.random() * names.length)];
                    createPlayer(name);
                    //System.out.println(map.get(name));
                    Thread.sleep(80000);
                } catch (InterruptedException e) {
                	e.printStackTrace();
                }
            }
        }
    }
    
    class KeyMonitor extends KeyAdapter{
        @Override
        public void keyPressed(KeyEvent e) {
    		if(e.getKeyCode() == KeyEvent.VK_A) {
    			playerList.get(0).moveDict.put("horizontal", -1);
    		}
    		if(e.getKeyCode() == KeyEvent.VK_D) {
    			playerList.get(0).moveDict.put("horizontal", 1);
    		}
    		if(e.getKeyCode() == KeyEvent.VK_W) {
    			playerList.get(0).moveDict.put("vertical", -1);
    		}
    		if(e.getKeyCode() == KeyEvent.VK_S) {
    			playerList.get(0).moveDict.put("vertical", 1);
    		}
    		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
    			playerList.get(0).moveDict.put("space", 1);
    		}
    		
    		if(playerMode == 2) {
	    		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
	    			playerList.get(1).moveDict.put("horizontal", -1);
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
	    			playerList.get(1).moveDict.put("horizontal", 1);
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_UP) {
	    			playerList.get(1).moveDict.put("vertical", -1);
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
	    			playerList.get(1).moveDict.put("vertical", 1);
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
	    			playerList.get(1).moveDict.put("space", 1);
	    		}
    		}
    		
    		if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
    			pause = !pause;
    		}
        }

        @Override
        public void keyReleased(KeyEvent e) {
        	if(e.getKeyCode() == KeyEvent.VK_A) {
        		playerList.get(0).moveDict.put("horizontal", 0);
    		}
    		if(e.getKeyCode() == KeyEvent.VK_D) {
    			playerList.get(0).moveDict.put("horizontal", 0);
    		}
    		if(e.getKeyCode() == KeyEvent.VK_W) {
    			playerList.get(0).moveDict.put("vertical", 0);
    		}
    		if(e.getKeyCode() == KeyEvent.VK_S) {
    			playerList.get(0).moveDict.put("vertical", 0);
    		}
    		if(e.getKeyCode() == KeyEvent.VK_SPACE) {
    			playerList.get(0).moveDict.put("space", 0);
    		}
    		
    		if(playerMode == 2) {
	    		if(e.getKeyCode() == KeyEvent.VK_LEFT) {
	        		playerList.get(1).moveDict.put("horizontal", 0);
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
	    			playerList.get(1).moveDict.put("horizontal", 0);
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_UP) {
	    			playerList.get(1).moveDict.put("vertical", 0);
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_DOWN) {
	    			playerList.get(1).moveDict.put("vertical", 0);
	    		}
	    		if(e.getKeyCode() == KeyEvent.VK_ENTER) {
	    			playerList.get(1).moveDict.put("space", 0);
	    		}
    		}
        }
    }
}
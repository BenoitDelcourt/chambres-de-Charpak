
import java.awt.*;
import java.util.*;
import java.awt.*;
import java.util.*;
import java.awt.event.*;
import java.text.*;
public class application_charpak{
    static appli_charpak applic_charpak;
    public static void main(String[] args) {
	applic_charpak=new appli_charpak("Fenetre initiale");
	applic_charpak.run();
    }
}
class appli_charpak extends Frame{
    static final float pi=(float)3.141592652;
    static final float eps0=1/(36*pi*10*(float)Math.pow((float)10000.0,2));
    static final int dim=31,top_stop=60,left_stop=20,bottom_stop=100,right_stop=100,top_stop_cree=420,left_stop_cree=20,bottom_stop_cree=460,right_stop_cree=200,top_cree=320,left_cree=20,bottom_cree=360,right_cree=200;
    final int 	top_demarre=300;
    final int 	left_demarre=50;
    final int 	bottom_demarre=480;
    final int 	right_demarre=800;
    Image image,image_method;
    public ensemble_de_chambres ensemble[]=new ensemble_de_chambres[2];
    int ppmouseh;int ppmousev;Graphics gr;
    boolean relachee=false,pressee=false,cliquee=false,draguee=false;
    long temps_en_sec=0;int i_run;
    boolean creation_ensembles,demo_a_faire=true,dessiner_menu_principal_ou_fin=false;
    long temps_initial_en_sec,temps_initial_en_sec_prec=0,temps_minimum=360;
    String d_ou_je_reviens;
    boolean fils_crees=false,terminer_demo=false,demo_faite=false;
    private SimpleDateFormat formatter; 
    Color orange_pale;
    int n_ensembles;
    private MouseStatic mm;//Thread Th1;
    boolean toutdebut,run_applet,dejapaint;
    Font times14=new Font("Times",Font.PLAIN,14);
    Font times_gras_14=new Font("Times",Font.BOLD,14);
    Font times_gras_24=new Font("Times",Font.BOLD,24);
    appli_charpak(String stri){
	super(stri);
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    if(ensemble[0]!=null){
			ensemble[0].dispose();
			ensemble[0]=null;
		    }
		    if(ensemble[1]!=null){
			ensemble[1].dispose();
			ensemble[1]=null;
		    }
		    dispose();
		    System.exit(0);
		};
	    });
	toutdebut=true;	    dejapaint=false;
	run_applet=true;
	System.out.println("init applet");
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	setBackground(Color.white);
	formatter=new SimpleDateFormat ("EEE MMM dd hh:mm:ss yyyy", Locale.getDefault());
	Date maintenant=new Date();orange_pale=new Color(140,90,0);
	temps_initial_en_sec=temps_en_secondes(maintenant);
	System.out.println("maintenant "+maintenant+" s "+temps_initial_en_sec);
	creation_ensembles=true;fils_crees=false;
	relachee=false;pressee=false;cliquee=false;draguee=false;
	pack();setVisible(true);	
	setSize(1000,800);
	setLocation(0,0);
	gr= getGraphics();
	String name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/charpak_premiere_page.jpg";
	image=createImage(400,400);
	Graphics gTTampon=image.getGraphics();
	image=Toolkit.getDefaultToolkit().getImage(name);
	MediaTracker tracker=new MediaTracker(this);
	tracker.addImage(image,1); 
	try {tracker.waitForAll(); }
	catch (InterruptedException e){
	    System.out.println(" image pas arrivee?");
	}
	//g.drawImage(image2,555,0,null);
	gr.drawImage(image,450,30,this);
	gTTampon.dispose();	
    }
    public long temps_en_secondes(Date nun){
	formatter.applyPattern("s");
	int s=Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("m");
	int m=Integer.parseInt(formatter.format(nun));
	formatter.applyPattern("h");
	int h=Integer.parseInt(formatter.format(nun));
	//System.out.println(" h "+h+" m "+m+" s "+s);
	return (h*3600+m*60+s);
    }    
    public void run(){
	int isleep=20;
	System.out.println(" Run ");
	relachee=false;pressee=false;cliquee=false;draguee=false;
    fin_de_programme:
	while (run_applet){
	    Date now=new Date();
	    temps_en_sec=temps_en_secondes(now);
	    //System.out.println("temps_en_sec "+temps_en_sec);
	    
	    if(temps_en_sec-temps_initial_en_sec>temps_minimum){
		run_applet=true;break fin_de_programme; 
	    }
	    //if((peindre&&(!creation_ensembless))||creation_ensembles)
	    if(toutdebut){
		dessiner_menu_principal_ou_fin=false;
		terminer_demo=false;
		if(demo_a_faire){
		    n_ensembles=0;
		    gr.drawImage(image,450,30,this);
		    for(int iii=0;iii<2;iii++)
			if(!cliquee){
			    creation_d_un_ensemble(iii,-1);
			    n_ensembles++;
			}else{
			    cliquee=false;
			    terminer_demo=true;
			    break;
			}
		    demo_a_faire=false;
		}
		if(!terminer_demo)
		    if(!cliquee){
			for(int iii=1;iii>=0;iii--)
			    if(ensemble[iii]!=null){ 
				if(ensemble[iii].comm!=null)
				    if(ensemble[iii].comm.vas_y_dessine){
					ensemble[iii].comm.dessine_image();
					ensemble[iii].comm.vas_y_dessine=false;
				    }
				if(ensemble[iii].command==""){
				    if(ensemble[iii].du_nouveau_a_voir)
					ensemble[iii].peint_ens();
				    //System.out.println(" cliquee "+cliquee);
				}else{
				    //System.out.println("commande "+ensemble[iii].command+" iii "+iii);
				    ensemble[iii].traite_commande();
				    //ensemble[iii].command="";
				}
			    }
			//System.out.println(" cliquee "+cliquee);
		    }else{
			terminer_demo=true;
			fermer_ensembles();
			toutdebut=false;
			dessiner_menu_principal_ou_fin=true;
			System.out.println(" terminer_demo ");
		    }
	    }else{
		System.out.println(" vers paint,run_applet "+run_applet);
		if(dessiner_menu_principal_ou_fin)
		    menu_principal_et_fin();
		System.out.println(" &&&&vers paint ");
		if(d_ou_je_reviens!=""){
		    System.out.println("d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles);
		    n_ensembles=0;
		}
		if(creation_ensembles)
		    demarrer_application();
		if(d_ou_je_reviens!=""){
		    System.out.println("***d_ou_je_reviens "+d_ou_je_reviens+" n_ensembles "+n_ensembles);
		    d_ou_je_reviens="";
		}

		
		//System.out.println(" apres creer_chambres");
		if(n_ensembles!=0)
		    for(int ii=0;ii<n_ensembles;ii++){
			if(ensemble[ii].comm!=null)
			    if(ensemble[ii].comm.vas_y_dessine){
				ensemble[ii].comm.dessine_image();
				ensemble[ii].comm.vas_y_dessine=false;
			    }
			if(ensemble[ii].du_nouveau_a_voir){
			    ensemble[ii].peint_ens();
			    if(ensemble[ii].le_virer){
				toutdebut=ensemble[ii].command=="Revenir a la page initiale avec infos";

				if(toutdebut){
				    demo_a_faire=true;
				    terminer_demo=false;
				    dessiner_menu_principal_ou_fin=false;
				    creation_ensembles=false;
				    eraserect(gr,0,0,1000,1200,Color.white);
				}else
				    dessiner_menu_principal_ou_fin=true;
				creation_ensembles=true;
				d_ou_je_reviens=ensemble[ii].command;
				fermer_ensembles();
				break;				
			    } 				    
			}
		    }
	    }
	    i_run++;if(i_run==20)i_run=0;
	    //System.out.println("isleep");
	    try {Thread.sleep(isleep);}
	    catch (InterruptedException signal){System.out.println("catch ");}
	}
	System.out.println(" run_applet "+run_applet);
	menu_principal_et_fin();
	for(int ii=0;ii<n_ensembles;ii++){
	    if(ensemble[ii].comm!=null)ensemble[ii].comm.dispose();
	    ensemble[ii].dispose();
	}
	dispose();
    }
    void demarrer_application(){ 
	//System.out.println("dem relachee "+relachee+" pressee "+pressee);
	if(cliquee){
	    //	if(relachee&&pressee){
	    System.out.println("11");
	    int xi=ppmouseh;int yi=ppmousev;
	    cliquee=false;
	    if ((xi > left_demarre)&&(xi < right_demarre)){
		for(int i=0;i<=3;i++){
		    if ((yi > top_demarre+i*20)&&(yi < top_demarre+(i+1)*20)){
			if(i<=1)
			    n_ensembles=2;
			else
			    n_ensembles=1;
			for(int iii=0;iii<n_ensembles;iii++)
			    creation_d_un_ensemble(iii,i);
			for(int iii=0;iii<n_ensembles;iii++){
			    ensemble[iii].du_nouveau_a_voir=true;
			    ensemble[iii].peint_ens();
			}
			relachee=false;pressee=false;
			creation_ensembles=false;
		    }
		}
	    }
	}		
    }
    public void creation_d_un_ensemble(int iii,int i_demarre){
	String str="";
	if(i_demarre==0)str="Chambre unique a 4 fils";
	if(i_demarre==1)str="Chambre a derive a fils";
	if(i_demarre<=1)
	    if(iii==0)
		str+=", fils au meme potentiel";
	    else
		str+=", fils a deux potentiels differents.";
	if(i_demarre==2)str="4 chambres a fils.";
	if(i_demarre==3)str="1 chambre a derive et 2 chambres a fils a potentiels alternes";
	System.out.println(" creation_d_un_ensemble iii "+iii+" i_demarre "+i_demarre);
	ensemble[iii]=new ensemble_de_chambres(str, iii,i_demarre, this);
	ensemble[iii].comment_init="";
	//setVisible(false);
	//ensemble[iii].setVisible(false);ensemble[iii].setVisible(true);
	System.out.println(" ensemble "+iii);
    }
    public  void menu_principal_et_fin(){
	System.out.println("deb dans paint, toutdebut"+toutdebut);
	System.out.println("passage dans paint,dejapaint "+dejapaint);
	dessiner_menu_principal_ou_fin=false;
	if(run_applet){
	    dejapaint=true;
	    eraserect(gr, 0,0,1000,1000,Color.white);
	    
	    
	    //if(creation_ensembles){
	    System.out.println(" top_cree "+top_cree);
	    int ppv;
	    gr.setColor(Color.red);
	    gr.setFont(times_gras_24);
	    gr.drawString("Cliquez dans ce menu pour un ensemble de chambres de votre choix.",left_demarre, top_demarre-20);	      
	    gr.setFont(times14);
	    paintrect_couleur(gr,top_demarre,left_demarre,bottom_demarre,right_demarre,Color.red);
	    gr.setFont(times_gras_24);
	    gr.setColor(Color.black);
	    gr.setColor(Color.blue);gr.setFont(times14);
	    drawline_couleur(gr,left_demarre, top_demarre+20, right_demarre, top_demarre+20, Color.red);
	    gr.drawString("Chambre unique a 4 fils au meme potentiel ou potentiels alternes.",left_demarre+10, top_demarre+14);//0
	    drawline_couleur(gr,left_demarre, top_demarre+40, right_demarre, top_demarre+40, Color.red);
	    gr.drawString("Chambre a derive a fils au meme potentiel ou potentiels alternes.",left_demarre+10, top_demarre+34);//1
	    drawline_couleur(gr,left_demarre, top_demarre+60, right_demarre, top_demarre+60, Color.red);
	    gr.drawString("Detecteur avec 4 chambres a fils en coincidence.",left_demarre+10, top_demarre+54);//2
	    drawline_couleur(gr,left_demarre, top_demarre+80, right_demarre, top_demarre+80, Color.red);
	    gr.drawString("Detecteur avec une chambre a derive et 2 chambres a fils en coincidence.",left_demarre+10, top_demarre+74);//3
	    drawline_couleur(gr,left_demarre, top_demarre+100, right_demarre, top_demarre+100, Color.red);
	    gr.drawString("",left_demarre+10, top_demarre+94);//4
	    drawline_couleur(gr,left_demarre,top_demarre+120, right_demarre, top_demarre+120, Color.red);
	    gr.drawString("",left_demarre+10, top_demarre+114);//5
	    drawline_couleur(gr,left_demarre,top_demarre+140, right_demarre,top_demarre+140, Color.red);
	    drawline_couleur(gr,left_demarre,top_demarre+160, right_demarre,top_demarre+160, Color.red);
	}else{
	    System.out.println("erase gr ");
	    eraserect( gr,0,0,1000,1000,Color.white);
	    gr.setFont(times_gras_24);gr.setColor(Color.black);
	    if(temps_en_sec-temps_initial_en_sec>temps_minimum){
		for(int i=0;i<20;i++)
		    gr.drawString("TEMPS MAXIMUM EXPIRE",100,100);
	    }
	    for(int i=0;i<20;i++){
		gr.drawString("FIN DU PROGRAMME",100,150);
		gr.drawString("POUR REVENIR A INTERNET, SUPPRIMEZ CETTE FENETRE.",100,200);
	    }
	}
    }
    void fermer_ensembles(){
	eraserect(gr,0,0,1000,1000,Color.white);	
	for(int i=0;i<n_ensembles;i++){
	    if(ensemble[i].comm!=null)
		ensemble[i].comm.dispose();
	    ensemble[i].dispose();
	    ensemble[i]=null;
	}	
	//setVisible(true);
	d_ou_je_reviens="je reviens de num_fen";
	n_ensembles=0;
	cliquee=false;
	relachee=false;
	pressee=false;
    }
    void drawline_couleur(Graphics g,int xin, int yin, int xfin, int yfin, Color couleur)
    {
	g.setColor(couleur);g.drawLine(xin,yin,xfin,yfin);
    }
    void paintrect_couleur(Graphics g,int top, int left, int bot, int right, Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.drawRect(x,y,width,height);
    }    
    void remplisrect(Graphics g,int top, int left, int bot, int right, Color couleur)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(couleur);g.fillRect(x,y,width,height);
    }    
    
    void paintrect(Graphics g,int top, int left, int bot, int right)
      
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    g.setColor(Color.black);g.drawRect(x,y,width,height);
    }
    void paintcircle(Graphics g,int xx, int yy, int rr)
      
    {int x,y,r;
    x=xx;y=yy;r=rr;
    g.setColor(Color.blue);g.fillOval(x,y,r,r);
    }
    
    
  //   void eraserect(Graphics g, int top, int left, int bot, int right)
  //{int x,y,width,height;
  //x=left;y=top;width=right-left;height=bot-top;
    //g.setColor(Color.white);g.fillRect(x,y,width,height);
  //}
    void eraserect(Graphics g, int top, int left, int bot, int right,Color couleur){
	//System.out.println("couleur "+couleur);
	int x,y,width,height;
	x=left;y=top;width=right-left;height=bot-top;
	g.setColor(couleur);g.fillRect(x,y,width,height);
    }
    void invertrect(Graphics g,int top, int left, int bot, int right)
    {int x,y,width,height;
    x=left;y=top;width=right-left;height=bot-top;
    //g.setColor(Color.black);g.fillRect(x,y,width,height);
    }
    void paintcircle_couleur (Graphics g,long x,long y, long r,Color couleur)
    {
	g.setColor(couleur);g.fillOval((int)(x-r/2),(int)(y-r/2),(int)r,(int)r);
    }
    public void	traite_click(){
	System.out.println("entree traite_click "+"cliquee "+cliquee+"pressee "+pressee+"relachee "+relachee);
	Date maintenant=new Date();
	temps_initial_en_sec=temps_en_secondes(maintenant);
	if(cliquee){
	    if(temps_initial_en_sec<temps_initial_en_sec_prec+2){
		cliquee=false;pressee=false;relachee=false;
	    }else
		temps_initial_en_sec_prec=temps_initial_en_sec;
	}
	if(cliquee&&!toutdebut&&n_ensembles!=0){
	    cliquee=false;pressee=false;relachee=false;
	    for(int ik=0;ik<n_ensembles;ik++){    
		ensemble[ik].le_virer=true;
		ensemble[ik].command="Revenir a la fenetre principale";
	    }
	}
    }
    class MouseStatic extends MouseAdapter{
	appli_charpak subject;
	public MouseStatic (appli_charpak a){
	    subject=a;
	}
	public void mouseClicked(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //for( int iq=0;iq<ens_de_cyl[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();cliquee=true;relachee=true;
	    System.out.println("relachee "+relachee);
	}
    }
}

class ensemble_de_chambres extends Frame implements ActionListener{
    static final int top_ens_cyl=0,left_ens_cyl=10,bottom_ens_cyl=350,right_ens_cyl=800,top_ens_cyl1=0,left_ens_cyl1=0,bottom_ens_cyl1=800,right_ens_cyl1=500;
    float ddd=(float)0.,scala=(float)0.,dist_min=1000;point toto,toto1;
    float coco=(float)0.,cucu=(float)0.,caca=(float)0.,cici=(float)0.,cece=(float)0.;
    float deux_pi_eps0=0;
    int coco_int=0,cucu_int=0,caca_int=0,cece_int=0,i_fil=0;
    static final float pixels_par_metre=(float)2.E4;
    static final float vitesse_ohm=(float)1.5;
    public chambre kammer[]=new chambre[6];
    long temps_initial_en_sec_prec=0;
    boolean fin_gerelesmenus_avec_souris,ret,stopper,du_nouveau_a_voir,occupied=false,le_virer=false;
    int ppmouseh,ppmousev,ppmouseh_prec,ppmousev_prec;
    String command_prec="",command="",comment="Utilisez les menus",comment_init="",comment_prec="";
    private TextField tf;private keyList kl;boolean key_entered;
    Graphics grp_ensemble;
    public electr electron[]=new  electr[200];
    private MouseStatic mm;int nb_mouse;private MouseMotion motion;
    int n_paint; boolean relachee,pressee,cliquee,draguee;
    int n_counts;int i_demarre,n_chambres;
    static commentaire comm;
    int i_ens;appli_charpak subject;MenuBar mb1;
    int nimage=10,npt_equipot=0;boolean montrer_cosmiques,equipoting=false;
    boolean calcul_equip,show_forces;boolean trouve_deplacement;
    point point_in,point_f;int bandeau_x,bandeau_y;
    ensemble_de_chambres(String s, int i_ens1,int i_demarre1,appli_charpak sub1){
	super(s);
	subject=sub1;
	deux_pi_eps0=2*subject.pi*subject.eps0;
        addWindowListener(new java.awt.event.WindowAdapter() {
		public void windowClosing(java.awt.event.WindowEvent e) {
		    le_virer=true;du_nouveau_a_voir=true;
		    command="Revenir a la page principale";
		};
	    });
	i_demarre=i_demarre1;
	i_ens=i_ens1;command="";
	bandeau_x=20;
	if(i_demarre<=0||i_demarre==2)
	    bandeau_y=60;
	else if(i_demarre==3){
	    bandeau_x=200;
	    bandeau_y=bottom_ens_cyl1-170;;
	}else
	    bandeau_y=bottom_ens_cyl1-100;
	mb1=null;
	barre_des_menus();
	toto=new point((float)0.,(float)0.);
	toto1=new point((float)0.,(float)0.);
	montrer_cosmiques=false;equipoting=false;
	point_in=new point((float)0.,(float)0.);point_f=new point((float)0.,(float)0.);
	mm=new MouseStatic(this);
	this.addMouseListener(mm);
	motion=new MouseMotion(this);
	this.addMouseMotionListener(motion);
	pack();
	if(i_demarre==-1){
	    setSize(right_ens_cyl/2-left_ens_cyl/2,bottom_ens_cyl-top_ens_cyl);
	    setLocation(left_ens_cyl,top_ens_cyl+i_ens*350);
	}
	if(i_demarre==0){
	    setSize(right_ens_cyl-left_ens_cyl,bottom_ens_cyl-top_ens_cyl);
	    setLocation(left_ens_cyl,top_ens_cyl+i_ens*350);
	}
	if(i_demarre==1){
	    setSize(right_ens_cyl1-left_ens_cyl1,bottom_ens_cyl1-top_ens_cyl1);
	    setLocation(left_ens_cyl1+i_ens*500,top_ens_cyl1);
	}
	if(i_demarre>1){
	    setSize(right_ens_cyl-left_ens_cyl,2*(bottom_ens_cyl-top_ens_cyl)+50);
	    setLocation(left_ens_cyl,top_ens_cyl);
	}
	setVisible(true);
	grp_ensemble=getGraphics();
	du_nouveau_a_voir=true;
	if(i_demarre<=1)
	    n_chambres=1;
	if(i_demarre==2)n_chambres=4;
	if(i_demarre==3)n_chambres=3;
	for(int i=0;i<n_chambres;i++)
	    if(i_demarre<=0)
		if(i_ens==0)
		    kammer[i]=new chambre_a_fils_horizontale(i,false);
		else
		    kammer[i]=new chambre_a_fils_horizontale(i,true);
	    else if(i_demarre==1)
		if(i_ens==0)
		    kammer[i]=new chambre_a_fils_verticale(i,false);
		else
		    kammer[i]=new chambre_a_fils_verticale(i,true);
	    else if(i_demarre==2)
		kammer[i]=new chambre_a_fils_horizontale(i,false);
	    else if(i_demarre==3)
		if(i==0||i==2)
		    kammer[i]=new chambre_a_fils_horizontale(i,false);
		else
		    kammer[i]=new chambre_a_fils_verticale(i,true);
	for(int i=0;i<n_chambres;i++)
	    System.out.println("kammer[i].pot_alternes "+kammer[i].pot_alternes );
    }
    static class commentaire extends Frame{
	final int top=100,left=250,bottom=600,right=830;
	//final int top=0,left=0,bottom=700,right=900;
	boolean vas_y_dessine=true;
	int i_demarre;int nb_lignes=10;
	Graphics grp_c; Image image_method;Graphics gTTampon;MediaTracker tracker1;
	commentaire(String s){
	    super(s);
	    addWindowListener(new java.awt.event.WindowAdapter() {
		    public void windowClosing(java.awt.event.WindowEvent e) {
			comm=null;
			dispose();
		    };
		});
	    //pack();
	    setSize(right-left,bottom-top);
	    setLocation(left,top);
	    setVisible(true);
	    grp_c= getGraphics();	    
	    System.out.println("cree graphe "+s+" i_demarre "+i_demarre );
	    String name="C:/Users/benoit Delcourt/Desktop/j2sdk1.4.2_04/bin/charpak_method.jpg";
	    image_method=createImage(400,400);
	    gTTampon=image_method.getGraphics();
	    image_method=Toolkit.getDefaultToolkit().getImage(name);
	    tracker1=new MediaTracker(this);
	    tracker1.addImage(image_method,1); 
	    try {tracker1.waitForAll(); }
	    catch (InterruptedException e){
		System.out.println(" image pas arrivee?");
	    }
	}
	void dessine_image(){
	    for (int ich=0;ich<1;ich++)
		grp_c.drawImage(image_method,30,60,this);
	}
    }
    public void barre_des_menus(){
	System.out.println("i_demarre  "+i_demarre);
	mb1=new MenuBar();
	if(i_demarre<=1){
	    Menu operations_sur_elements= new Menu("modifier la chambre");
	    MenuItem itepq3=new MenuItem("deplacer une plaque");
	    MenuItem itep3=new MenuItem("deplacer un fil");
	    MenuItem itep1=new MenuItem("eliminer un fil");
	    MenuItem itepp=new MenuItem("creer un fil sensible");
	    MenuItem iterr=new MenuItem("creer un fil de champ");
	    operations_sur_elements.add(itepq3);
	    itepq3.addActionListener(this);
	    operations_sur_elements.add(itep3);
	    itep3.addActionListener(this);
	    operations_sur_elements.add(itep1);
	    itep1.addActionListener(this);
	    operations_sur_elements.add(itepp);
	    itepp.addActionListener(this);
	    operations_sur_elements.add(iterr);
	    iterr.addActionListener(this);
	    if(i_demarre!=-1){
		MenuItem itep=new MenuItem("augmenter v_champ de 150V");
		MenuItem iter=new MenuItem("diminuer v_champ de 150V");
		operations_sur_elements.add(itep);
		itep.addActionListener(this);
		operations_sur_elements.add(iter);
		iter.addActionListener(this);
	    }
	    mb1.add(operations_sur_elements);
	}
	Menu actions= new Menu("Actions");
	if(i_demarre<=1){
	    MenuItem itep00=new MenuItem("montrer chambre");
	    MenuItem itep11=new MenuItem("montrer_forces");
	    MenuItem itep22=new MenuItem("montrer champs supplementaires");
	    actions.add(itep11);
	    itep11.addActionListener(this);
	    if((i_demarre<=0)||(i_demarre==1)){
		MenuItem itep111=new MenuItem("montrer_equipotentielles");
		actions.add(itep111);
		itep111.addActionListener(this);
	    }
	    actions.add(itep22);
	    itep22.addActionListener(this);
	    actions.add(itep00);
	    itep00.addActionListener(this);
	}
	MenuItem itep33=new MenuItem("montrer 3 cosmiques");
	if((i_demarre==1)||(i_demarre==3))itep33=new MenuItem("montrer un cosmique");
	actions.add(itep33);
	itep33.addActionListener(this);
	mb1.add(actions);

	Menu meth= new Menu("Methode utilisee");
	MenuItem itebc1=new MenuItem("Methode utilisee");
	itebc1.addActionListener(this);
	meth.add(itebc1);
	mb1.add(meth);

	if(i_demarre!=-1){
	    Menu revenir= new Menu("Sortir");
	    MenuItem iteb1=new MenuItem("Revenir a la page principale");
	    revenir.add(iteb1);
	    iteb1.addActionListener(this);
	    MenuItem iteb11=new MenuItem("Revenir a la page initiale avec infos");
	    revenir.add(iteb11);
	    iteb11.addActionListener(this);
	    MenuItem iteb12=new MenuItem("Sortir du programme");
	    revenir.add(iteb12);iteb12.addActionListener(this);
	    mb1.add(revenir);
	}
	setMenuBar(	mb1);
	//pack();setVisible(true);
    }
    public  void peint_ens(){
	if(du_nouveau_a_voir){
	    if(!le_virer)
	     du_nouveau_a_voir=false;
		System.out.println(" peint_ens i_ens "+i_ens);
	    subject.eraserect(grp_ensemble,0,0,1000,1000,Color.white);
	    ecrire_bandeau();
	    grp_ensemble.setColor(Color.blue);
	    for(int i=0;i<n_chambres;i++)
		kammer[i].paint();
	}
    }	
    public void	traite_click(){
	System.out.println("entree traite_click ");
	int xi=ppmouseh;boolean ret,dg;
	int yi=ppmousev;
	for (int ich=0;ich<n_chambres;ich++)
	    kammer[ich].pt_supplementaire=false;
	//System.out.println("xi "+xi+" yi "+yi);
	if(cliquee){
	    Date maintenant=new Date();
	    subject.temps_initial_en_sec=subject.temps_en_secondes(maintenant);
	    if(!((ppmouseh==ppmouseh_prec)&&(ppmousev==ppmousev_prec))){
		ppmouseh_prec=ppmouseh;ppmousev_prec=ppmousev;	
	    }else
		cliquee=false;
	}
	System.out.println(" command "+command+" equipoting "+equipoting);
	//if(i_demarre==-1&&command=="deplacer un fil")
	//kammer[101]=null;
	if(command!=""){
	    if(!equipoting){
		command_prec=command;
		dg=gerelesmenus_avec_souris();
		if(fin_gerelesmenus_avec_souris)
		    command="";
	    }
	    else
		comment="Attendez la fin du calcul des equipotentielles";
	}
	// System.out.println("sortie traite_click ");
    }
    void drawline_couleur(Graphics g,int xin,int yin,int xfin,int yfin,Color couleur){
	//System.out.println("command "+command);
	if(command!="Revenir a la page principale")
	    g.setColor(couleur);g.drawLine(xin,yin,xfin,yfin);
    }
    public void actionPerformed(ActionEvent e){
	command=e.getActionCommand();
	System.out.println(command);
	//while(occupied){
	//}
	if(command!="")
	    traite_commande ();
	//return;
    }
    public void traite_commande (){
	for (int ich=0;ich<n_chambres;ich++)
	    kammer[ich].pt_supplementaire=false;
	relachee=false;
	if(command!=""){
	    fin_gerelesmenus_avec_souris=false;
	    Date maintenant=new Date();
	    subject.temps_initial_en_sec=subject.temps_en_secondes(maintenant);
	}
	if (command=="Revenir a la page principale"||command=="Sortir du programme"||command=="Revenir a la page initiale avec infos"){
	    le_virer=true;
	    du_nouveau_a_voir=true;
	}
	//deplace_plan=false;
	if(command!="")
	    fin_gerelesmenus_avec_souris=false;
	else
	    calcul_equip=false;
	if (command=="Methode utilisee"){
	    if(comm==null){
		comm=new commentaire(command+" i_ens "+i_ens);
		System.out.println(" image dessinee");
		comm.dessine_image();
	    }
	    command="";
	}
	if((command !="montrer 3 cosmiques"&&command !="montrer un cosmique"&&command!="montrer champs supplementaires")&&!le_virer)
	    du_nouveau_a_voir=false;
	if (command=="montrer chambre"){
	    comment=command ;
	    show_forces=false;calcul_equip=true;kammer[0].paint();
	    command="";
	    }
	if (command=="augmenter v_champ de 150V"){
	    comment="  v_champ augmente de 150V" ;
	    ecrire_bandeau();
	    kammer[0].v_champ+=(float)150.;kammer[0].calculs();calcul_equip=true;
	    kammer[0].paint();
	    command="";
	}
	if (command=="diminuer v_champ de 150V"){
	    comment="  v_champ diminuee de 150V" ; ;
	    ecrire_bandeau();
	    kammer[0].v_champ-=(float)150.;kammer[0].calculs();calcul_equip=true;
	    kammer[0].paint();
	    command="";
	}
	if (command=="montrer champs supplementaires"){
	    comment="Pour voir le champ en un point cliquez en ce point";
	    ecrire_bandeau();
	    show_forces=false;
	    }
	if (command=="montrer_equipotentielles"){
	    comment=command ;
		show_forces=false;calcul_equip=true;kammer[0].paint();
	    command="";
	    }
	if ((command =="montrer 3 cosmiques")||(command =="montrer un cosmique")){
	    montrer_les_cosmiques();
	    command="";
	}
	
	if(command=="eliminer un fil"||command=="deplacer un fil"||command=="creer un fil sensible"||command=="creer un fil de champ"||command=="deplacer une plaque"){
	    du_nouveau_a_voir=false;
	    calcul_equip=false;
	    kammer[0].n_pt_chp=0;show_forces=false;comment=" ";
	    }
	if(command=="creer un fil sensible"||command=="creer un fil de champ"){
	    comment=" Cliquez a l'endroit ou vous voulez avoir un nouveau fil";
	    ecrire_bandeau();
	}
	if(command=="eliminer un fil"){
	    comment=" Cliquez sur le fil que vous voulez eliminer";
	    ecrire_bandeau();
	}
	if(command=="deplacer un fil"||command=="deplacer une plaque"){
	    comment=" Faites glisser le fil ou la plaque avec la souris";
	    ecrire_bandeau();
	}
	if(command=="montrer_forces"){
	    show_forces=true;calcul_equip=false;
	    kammer[0].n_pt_chp=0;
	    if (! kammer[0].calculs_faits)
		kammer[0].calculs();
	    kammer[0].paint();
	    command="";
	}
	
	//System.out.println("command "+command);
	//return;
    }
    void montrer_les_cosmiques(){
	System.out.println(" debut montrer_cosmiques:");
	montrer_cosmiques=true;
	int n_part_a_montrer=3;
	if(command =="montrer un cosmique")
	    n_part_a_montrer=1;
	command="";
	calcul_equip=false;
	du_nouveau_a_voir=true;show_forces=false;kammer[0].n_pt_chp=0; 
	int cosmique=0;
	while((cosmique<n_part_a_montrer)&&(command=="")){
	    occupied=true;
	    Date maintenant=new Date();
	    subject.temps_initial_en_sec=subject.temps_en_secondes(maintenant);
	    
	    int dt_random=(int)((float)Math.random()*10);
	    System.out.println(" subject.temps_initial_en_sec "+subject.temps_initial_en_sec+" temps_initial_en_sec_prec "+temps_initial_en_sec_prec+" dt_random "+dt_random);
	    if(subject.temps_initial_en_sec-temps_initial_en_sec_prec>dt_random){
		cosmique++;
		temps_initial_en_sec_prec=subject.temps_initial_en_sec;
		subject.temps_initial_en_sec=subject.temps_en_secondes(maintenant);
		System.out.println(" cosmique "+cosmique);
		int nelec=0;
		int nreel=0;
		du_nouveau_a_voir=false;
		kammer[0].initialise_point_in_pointf();
		toto.assigne(point_f.x-point_in.x,point_f.y-point_in.y);
		if((i_demarre<=0)||(i_demarre==2)){
		    point_in.print("n_chambres "+n_chambres+" point point_in ");
		    point_f.print("point point_f ");
		    //toto.print(" toto ");
		    for (int ich=0;ich<n_chambres;ich++){
			point point_in_ch=new point(point_in);
			point point_f_ch=new point(point_f);
			if(ich!=0){
			    float facteur=(kammer[ich].plans_yd(0)-point_in.y)/(point_f.y-point_in.y);
			    point_in_ch.additionne_point_facteur(toto,facteur);
			}
			if(ich!=n_chambres-1){
			    float facteur=(kammer[ich].plans_yd(1)-point_f.y)/(point_f.y-point_in.y);
			    point_f_ch.additionne_point_facteur(toto,facteur);
			}
			float dist=point_in_ch.distance(point_f_ch);
			kammer[ich].nreel_max=(int)((float)30.*dist/(pixels_par_metre/200));
			int nelec_ch=(int)(float)Math.round(((float)Math.random()+(float)Math.random())/(float)2.*kammer[ich].nreel_max);
			point_in_ch.print( "ich "+ich+" dist "+dist+"point_in_ch ");
			kammer[ich].premier_electron=nelec;
			kammer[ich].nb_electron=nelec_ch;
			for (int iel =0;iel< nelec_ch;iel++)
			    electron[nelec+iel]=new electr(point_in_ch,point_f_ch,ich);
			point_f_ch.print("kammer[ich].premier_electron "+kammer[ich].premier_electron+" nelec_ch "+nelec_ch+"point_f_ch ");
			nelec+=nelec_ch;
			nreel+=nelec_ch;
			
		    }
		}else{
		    if(i_demarre==1){
			float dist=point_in.distance(point_f);   
			kammer[0].nreel_max=(int)((float)30.*dist/(pixels_par_metre/200));
			nelec=(int)(float)Math.round(((float)Math.random()+(float)Math.random())/(float)2.*kammer[0].nreel_max);
			nreel=nelec;
			for (int iel =0;iel< nelec;iel++)
			    electron[iel]=new electr(point_in,point_f,0);
			kammer[0].premier_electron=0;
			kammer[0].nb_electron=nelec;
		    }
		    else if(i_demarre==3){
			point point_f_ch=new point(point_f);
			float facteur=(kammer[0].plans_yd(1)-point_f.y)/(point_f.y-point_in.y);
			point_f_ch.additionne_point_facteur(toto,facteur);
			float dist=point_in.distance(point_f_ch);
			kammer[0].nreel_max=(int)((float)30.*dist/(pixels_par_metre/200));
			int nelec_ch=(int)(float)Math.round(((float)Math.random()+(float)Math.random())/(float)2.*kammer[0].nreel_max);
			for (int iel =0;iel< nelec_ch;iel++)
			    electron[iel+nelec]=new electr(point_in,point_f_ch,0);
			kammer[0].premier_electron=0;
			kammer[0].nb_electron=nelec_ch;
			nelec+=nelec_ch;
			point_in.print("00000nelec_ch "+nelec_ch+" point_in ");
			point_f_ch.print(" point_f_ch ");
			
			point point_in_ch=new point(point_in);
			point_f_ch=new point(point_f);
			facteur=(kammer[0].plans_yd(1)+10-point_in.y)/(point_f.y-point_in.y);
			point_in_ch.additionne_point_facteur(toto,facteur);
			facteur=(kammer[n_chambres-1].plans_yd(0)-10-point_f.y)/(point_f.y-point_in.y);
			point_f_ch.additionne_point_facteur(toto,facteur);
			dist=point_in_ch.distance(point_f_ch);
			kammer[1].nreel_max=(int)((float)30.*dist/(pixels_par_metre/200));
			nelec_ch=(int)(float)Math.round(((float)Math.random()+(float)Math.random())/(float)2.*kammer[1].nreel_max);
			kammer[1].premier_electron=nelec;
			kammer[1].nb_electron=nelec_ch;
			for (int iel =0;iel< nelec_ch;iel++)
			    electron[iel+nelec]=new electr(point_in_ch,point_f_ch,1);			
			nelec+=nelec_ch;
			nreel+=nelec_ch;
			kammer[1].nreel_max=(int)((float)30.*dist/(pixels_par_metre/100));
			
			
			point_in_ch.print("11111nelec_ch "+nelec_ch+" point_in_ch ");
			point_f_ch.print(" point_f_ch ");
			
			point_in_ch=new point(point_in);
			facteur=(kammer[n_chambres-1].plans_yd(0)-point_in.y)/(point_f.y-point_in.y);
			point_in_ch.additionne_point_facteur(toto,facteur);
			dist=point_in_ch.distance(point_f);
			kammer[2].nreel_max=(int)((float)30.*dist/(pixels_par_metre/200));
			nelec_ch=(int)(float)Math.round(((float)Math.random()+(float)Math.random())/(float)2.*kammer[2].nreel_max);
			point_in_ch.print("22222nelec_ch "+nelec_ch+" point_in_ch ");
			point_f.print(" point_f ");
			kammer[2].premier_electron=nelec;
			kammer[2].nb_electron=nelec_ch;
			for (int iel =0;iel< nelec_ch;iel++)
			    electron[iel+nelec]=new electr(point_in_ch,point_f,2);	
			nelec+=nelec_ch;
			if(i_demarre==1)
			    nreel+=nelec_ch;
		    }
		}
		if (nelec > 200) 
		    nelec=200;
		if (nreel > 200) 
		    nreel=200;
		System.out.println("nelec "+nelec+" nreel "+nreel);
		//		      subject.paintcircle(g,x,y,2);
		//invertcircle(g,(float)Math.round(xin),(float)Math.round(yin),2);
		//invertcircle(g,x,y,2);
		int laps=0,lapsmax=500;
		if((i_demarre==1)||(i_demarre==3))
		    lapsmax=400;
		grp_ensemble.setColor(Color.orange);
		while((nreel>1)&&(laps<lapsmax)){
		    laps++;
		    //System.out.println("laps "+laps+" nreel "+nreel);
		    if(laps==1){
			grp_ensemble.setColor(Color.black);
			subject.eraserect(grp_ensemble,0,0,1000,1000,Color.white);
			for(int ich=0;ich<n_chambres;ich++){
			    kammer[ich].dessine_plans();
			    for (int ir=0 ;ir<kammer[ich].nfils;ir++)
				kammer[ich].fil[ir].dessine_fil();
			}
		    }
		    grp_ensemble.setColor(Color.blue);
		    comment=" cosmique "+cosmique;
		    ecrire_bandeau();
		    //System.out.println("command "+command);
		    //if (command=="Revenir a la page principale")
		    //  retour_page_principale();
		    for (int ich=0;ich<n_chambres;ich++){
			boolean fixer=((i_demarre==3)&&(laps>1)&&((ich==0)||(ich==2)));
			if(!fixer)gere_electrons_chambre(ich,kammer[ich].nb_electron,kammer[ich].premier_electron,kammer[ich].nreel_max,nelec,nreel,laps);
		    }
		}
		//System.out.println(" avant");
		for (int iel =0;iel< nelec;iel++)
		    electron[iel]=null;
		//System.out.println(" apres");
	    }
	    occupied=false;
	}
	comment=" cosmique "+cosmique+ " utilisez le menu ";
	ecrire_bandeau();
	montrer_cosmiques=false;
    }
    
    void gere_electrons_chambre(int num_cha,int nb_elec_cha,int premier_elec,int nreel_max,int nelec_tot,int nreel,int laps){
	int nfois0=0;int ielec=premier_elec;
	int ielec0=-1;
	int ij=premier_elec;
	while((ielec0==-1)&&(ij<premier_elec+nb_elec_cha)){
	    if(i_demarre==3){
		if((electron[ij].num_cha==1)&&!electron[ij].elimi)
		    ielec0=ij;
	    }else
		if(!electron[ij].elimi)
		    ielec0=ij;
	    ij++;
	}
	int nfake=(int)(nreel_max*(float)1.5);
	while((nfake>=0)||((ielec<premier_elec+nb_elec_cha))){
	    if(ielec==ielec0){
		nfake--;
		nfois0++;
	    }
	    //if((num_cha>0)&&(ielec!=ielec0))System.out.println("ielec "+ielec+" nb_elec_cha "+nb_elec_cha +" nfake "+nfake+" nfois0 "+nfois0);
	    if(!electron[ielec].elimi){
		
				//electron[ielec].ptreal.print(" electron[ielec].ptreal ");
		boolean bool=!((i_demarre==3)&&((num_cha==0)||(num_cha==2)))||(laps==1);
				//System.out.println(" ielec "+ielec+"bool "+bool+" electron[ielec].num_cha "+electron[ielec].num_cha+" electron[ielec].elimi "+electron[ielec].elimi); 
		if(bool&&(nreel>1)){
		    point chp=kammer[num_cha].champ((int)(float)Math.round(electron[ielec].ptreal.x),(int)(float)Math.round(electron[ielec].ptreal.y));
				//System.out.println("ex,ey,ielec,electron[ielec].elimi"+ex+" "+ey+" "+ielec+" "+electron[ielec].elimi);
		    float fact;
		    if(kammer[num_cha].horizontale)
			fact=(float)30.;
		    else
			fact=(float)10.;
		    ddd=(float)Math.pow(vitesse_ohm/pixels_par_metre/fact,2)*chp.carre();
		    //System.out.println("chp.longueur() "+chp.longueur());
		    cucu_int=(int)(float)Math.round(electron[ielec].ptreal.x);
		    coco_int=(int)(float)Math.round(electron[ielec].ptreal.y);
		    grp_ensemble.setColor(Color.white);
		    for (int jj=-1;jj<=1;jj++)
			grp_ensemble.drawLine(cucu_int-1,coco_int+jj,cucu_int+1,coco_int+jj);
		    //subject.paintcircle_couleur(grp_ensemble,(int)(float)Math.round(electron[ielec].ptreal.x),(int)(float)Math.round(electron[ielec].ptreal.y),4,Color.white);
		    if((!(ielec==ielec0))||(nfois0==1))
			electron[ielec].ptreal.additionne_point_facteur(chp,-vitesse_ohm/pixels_par_metre/fact);
		    else{
			electron[ielec].ptreal.additionne_point_facteur(chp,(float)0.);
			//System.out.println(" ielec "+ielec+" ielec0 "+ielec0+" nfois0 "+nfois0+" nelec_tot "+nelec_tot);
		    }
		    //pour eviter les encalminages.
		    if(!kammer[num_cha].horizontale&&((float)Math.abs(chp.x)<(float)Math.abs(chp.y)/(float)50.))
			if((float)Math.random()>(float)0.2)
			    electron[ielec].ptreal.y+=(float)1.;
		    if(kammer[num_cha].horizontale&&((float)Math.abs(chp.y)<(float)Math.abs(chp.x)/(float)50.))
			if((float)Math.random()>(float)0.2)
			    electron[ielec].ptreal.x+=(float)1.;
		    
		    i_fil=electron[ielec].fil_elec;
		    toto1.assigne(kammer[num_cha].fil[i_fil].ptc.x-electron[ielec].ptreal.x,kammer[num_cha].fil[i_fil].ptc.y-electron[ielec].ptreal.y);
		    scala=toto1.scalaire(chp);
		    if (ddd > electron[ielec].dd||ddd >=(float)2.*(float)4.||chp.carre()<(float)1.e6||electron[ielec].dd<(float)2.*(float)4.||scala>0){
				//if ((ddd > electron[ielec].dd)||(chp.carre()<(float)1.)||(electron[ielec].dd<(float)2.*(float)2.)){
			electron[ielec].elimi= true;
			nreel--;
			if(!(scala>0))
			    kammer[num_cha].fil[electron[ielec].fil_elec].dessine_fil();
		    }else{
			dist_min=1000;
			for (int  iqq=i_fil-2;iqq<i_fil+3;iqq++){
			    if((iqq>=0)&&iqq<(kammer[num_cha].nfils)){
				if(kammer[num_cha].fil[iqq].sense){
				    if(kammer[num_cha].horizontale){
					 coco=(float)Math.abs(electron[ielec].ptreal.x- kammer[num_cha].fil[iqq].ptc.x);
					if (coco < dist_min){
					    dist_min=coco;
					    electron[ielec].fil_elec= iqq;
					}
				    }else{
					coco=(float)Math.abs(electron[ielec].ptreal.y- kammer[num_cha].fil[iqq].ptc.y);
					if (coco < dist_min){
					    dist_min=coco;
					    electron[ielec].fil_elec= iqq;
					}
				    }
				}
			    }
			}
			electron[ielec].dd=electron[ielec].ptreal.distance_carre(kammer[num_cha].fil[electron[ielec].fil_elec].ptc);
			int caca_int=(int)(float)Math.round(electron[ielec].ptreal.x);
			int cece_int=(int)(float)Math.round(electron[ielec].ptreal.y);
			grp_ensemble.setColor(Color.magenta);
			for (int jj=-1;jj<=1;jj++)
			    grp_ensemble.drawLine(caca_int-1,cece_int+jj,caca_int+1,cece_int+jj);

		    }
		}
	    }
	    if((ielec!=ielec0)||((ielec==ielec0)&&(nfake==-1)))
		if(ielec<premier_elec+nb_elec_cha-1)
		    ielec++;
	    else 
		break;
	}
    }
    public boolean gerelesmenus_avec_souris(){ 
	System.out.println("deb gerelesmenus_avec_souris ");
	if (command=="deplacer un fil"||command=="deplacer une plaque"){
	    boolean plaque=command=="deplacer une plaque";
	    ret=false;
	    if(draguee||trouve_deplacement){
		ret= kammer[0].glisser_vib(plaque);
	    }
	    if(pressee){
		System.out.println("avant glisser ");
		ret= kammer[0].glisser_vib(plaque);
	    }
	    if(relachee){
		ret= kammer[0].glisser_vib(plaque);
		if(!plaque)
		    kammer[0].symetrique=false;
		relachee=false;
		du_nouveau_a_voir=true;
		//kammer[0].paint();
		command="";
	    }
	}
	else if ((command=="creer un fil sensible")||command=="creer un fil de champ"){
	    if (relachee){
		relachee=false;
		boolean ccc=kammer[0].teste_souris(ppmouseh,ppmousev);
		if (ccc){
		    kammer[0].cree_un_fil(ppmouseh,ppmousev,(command=="creer un fil sensible"));
		    comment="fil cree ";
		    ecrire_bandeau();
		    command ="";fin_gerelesmenus_avec_souris=true;
		    du_nouveau_a_voir=true;calcul_equip=true;
		    kammer[0].calculs();kammer[0].calculs_faits=true;
		    //kammer[0].paint();
		}
	    }
	}
	else if (command=="eliminer un fil"){
	    boolean tr=false;	
	    if (pressee){
		calcul_equip=false;
		for(int iq=0;iq<kammer[0].nfils;iq++){
		    System.out.println("iq cherche "+iq);
		    kammer[0].fil[iq].deplaceq=false;
		    if (((float)Math.abs((float)ppmouseh-kammer[0].fil[iq].ptc.x) < 10 ) &&((float)Math.abs((float)ppmousev -kammer[0].fil[iq].ptc.y))< 10 ){
			comment="fil trouve ";
			ecrire_bandeau();
			tr=true;
			kammer[0].iq_dep=iq;
			for(int jq=kammer[0].iq_dep;jq<kammer[0].nfils-1;jq++)
			    kammer[0].fil[jq]=kammer[0].fil[jq+1];
			kammer[0].symetrique=false;
			du_nouveau_a_voir=true;
			command ="";
			System.out.println(" ********** iq trouve"+iq);
			}
		}
	    }
	    if(tr){
		kammer[0].nfils--;
		kammer[0].calculs();kammer[0].calculs_faits=true;kammer[0].paint();
		command ="";fin_gerelesmenus_avec_souris=true;
	    }
	    command="";
	    if(du_nouveau_a_voir)
		calcul_equip=true;
	    //tuefils();
	}
	if (command =="montrer champs supplementaires"){
	    //point chp=kammer[0].champ((int)(float)Math.round(kammer[0].fil[0].ptc.x),(int)(float)Math.round(kammer[0].fil[1].ptc.y-(kammer[0].fil[0].ptc.y-kammer[0].plans.yd[0])/4));
	    System.out.println(" toto");
	    if(relachee){
		System.out.println(" toto relachee");
		relachee=false;
		boolean ccc=kammer[0].teste_souris(ppmouseh,ppmousev);
		if (ccc){
		    System.out.println(" toto relachee ccc");
		    kammer[0].remplis_point_champ(ppmouseh,ppmousev,false);
		    kammer[0].pt_supplementaire=true;
		    kammer[0].e_pt[kammer[0].n_pt_chp-1].dessine((float)50.0 /kammer[0].norme_de_champ,(float)1.,Color.magenta);
		    command="";

		    //kammer[0].paint();
		}
	    }
	}

	System.out.println("fin gerelesmenus_avec_souris calcul_equip"+calcul_equip);
	if(du_nouveau_a_voir&&i_demarre==-1)
	    peint_ens();
	return true;
    } 
    void ecrire_bandeau(){
	if(comment!=comment_prec){
	    subject.eraserect(grp_ensemble,bandeau_y-20,bandeau_x,bandeau_y,bandeau_x+1000,Color.white);
	    grp_ensemble.setColor(Color.blue);
	    grp_ensemble.setFont(subject.times_gras_14);
	    grp_ensemble.drawString(comment,bandeau_x,bandeau_y);
	    grp_ensemble.setFont(subject.times14);
	    comment_prec=comment;
	}
    }
    
    class electr{
	int fil_elec;
	point ptreal; float dd;
	boolean elimi;int num_cha;
	electr(point p_in,point p_f,int num_cha1){
	    float rra;
	    num_cha=num_cha1;
	    elimi= false;rra=(float)Math.random();
	    ptreal= new point(p_in);
	    point titi=new point(p_f.x-p_in.x,p_f.y-p_in.y);
	    ptreal.additionne_point_facteur(titi,rra);
	    
	    //paintcircle(g,(float)Math.round(xereal),(float)Math.round(yereal),1);
	    float dmin=1000;
	    for (int  iq=0;iq<kammer[num_cha].nfils;iq++){
		if(kammer[num_cha].fil[iq].sense)
		    if(kammer[num_cha].horizontale){
			coco=(float)Math.abs(ptreal.x- kammer[num_cha].fil[iq].ptc.x);
			if (coco < dmin){
			    dmin=coco;
			    fil_elec= iq;
			}
		    }else{
			coco=(float)Math.abs(ptreal.y- kammer[num_cha].fil[iq].ptc.y);
			if (coco < dmin){
			    dmin=coco;
			    fil_elec= iq;
			}
		    }
	    }
	    dd=ptreal.distance_carre(kammer[num_cha].fil[fil_elec].ptc);	    
	}
    }
    class MouseMotion extends MouseMotionAdapter
    {
	ensemble_de_chambres subj;
	public MouseMotion (ensemble_de_chambres a){
	    subj=a;
	}
	public void mouseMoved(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();draguee=false;
	}
	public void mouseDragged(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();draguee=true;
	    //System.out.println("draguee dans Mousemove "+draguee);
	    traite_click();
	}
    }
    
    class MouseStatic extends MouseAdapter{
	ensemble_de_chambres subj;
	public MouseStatic (ensemble_de_chambres a){
	    subj=a;
	}
	public void mouseClicked(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    System.out.println("cliquee "+cliquee);
	    traite_click();
	    //	System.out.println("kammer[icylindre].nb_el_ens "+kammer[icylindre].nb_el_ens);
	    //System.out.println("icylindre "+icylindre);
	    //for( int iq=0;iq<kammer[icylindre].nb_el_ens;iq++)
	}
	public void mousePressed(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();pressee=true;relachee=false;
	    System.out.println("pressee "+pressee);
	    traite_click();
	}
	public void mouseReleased(MouseEvent e){
	    ppmouseh=e.getX();ppmousev=e.getY();
	    cliquee=true;
	    relachee=true;pressee=false;
	    System.out.println("relachee "+relachee);
	    traite_click();
	}
    }
    class keyList extends KeyAdapter
    { 
	int st;int carac_code;int puiss;
	public void keyList()
	{key_entered=false;puiss=1;st=0;
	}
	public void keyPressed(KeyEvent k)
	{
	    //st=k.getKeyCode()-48;key_entered=true;System.out.println("key "+st);
	    //gere_key();
	    carac_code=k.getKeyCode();System.out.println("carac_code  "+carac_code);
	    if(carac_code==KeyEvent.VK_DELETE||carac_code==10)
		{key_entered=true;
		tf.setText("");System.out.println(st);
		puiss=1;st=0;key_entered=false;
		}
	    else
		{puiss*=10;st=st*puiss+(k.getKeyCode()-48);
		System.out.println("st  "+st);
		}
	}
	
    }
    
    abstract class chambre {
	boolean horizontale=true,painting=false;
	int nfils,image,iessai;int n_paint=0;
	point chp;float vdepart;int xdepart,ydepart;
	champ_en_un_point e_pt[]=new champ_en_un_point[3000];
	public fil_tendu fil[]=new fil_tendu[50];
	boolean symetrique;
	int premier_electron=0,nb_electron=0,nreel_max=0;
	float v_sensible=(float)2000.,v_champ=(float)600.,norme_de_champ=(float)0.,norme_de_champ1=(float)0.;
	float dx_pt_souris,dy_pt_souris;
	int iq_dep,ip_dep;
	int n_pt_chp,i_chambre;boolean pt_supplementaire=false;
	float	rra,vref;
	boolean interieur,trouve,fixeequipot,calculs_faits;
	String nom_fichier,strplace1;
	char cen,forces,modif,placefils,rep,suitq;
	boolean pot_alternes=false;
        chambre(int i_chambre1,	boolean pot_alternes1){
	    chp=new point((float)0.,(float)0.);painting=false;
	    i_chambre=i_chambre1;
	    pot_alternes=pot_alternes1;
	    point pnt=new point((float)0.,(float)0.);
	    n_paint=0;iessai=0;
	    symetrique=true;
	    for (int i=0;i<3000;i++)
		e_pt[i]=new champ_en_un_point();
	    calcul_equip=(i_demarre<=0);
	    //calcul_equip=false;//%%%%
	    calculs_faits=false;
	    show_forces=false;
	    n_pt_chp=0;	
	}
	abstract float circulation(int iq,int iqprime,int i_chp_suppl);
	abstract void calcul_equipot();
	abstract void dessine_plans();
	abstract boolean glisser_plan();
	abstract boolean teste_souris(int x,int y);
	abstract void calc_images(fil_tendu wire,int coord);
	abstract void resultat_glisser_plans(int ip_dep,int xi,int yi);
	abstract void initialise_point_in_pointf();
	abstract boolean trouveequipot (int i,int ifil,point chp,point elecp,float v,float vp,int x,int y,int sens);
	abstract int plans_yd(int i_plan);

	public boolean glisser_vib ( boolean plaque){
	    int	xi,yi,i,iq,iii;boolean ret=false;
	    xi=ppmouseh;yi=ppmousev;System.out.println("glisser_vib trouve_deplacement"+trouve_deplacement+"pressee"+pressee);
	    if (pressee){
		if(!trouve_deplacement){
		    if(plaque){
			trouve_deplacement=glisser_plan();
		    }else{
			for(iq=0;iq<nfils;iq++){
			    System.out.println("iq cherche "+iq);
			    fil[iq].deplaceq=false;
			    if (((float)Math.abs((float)xi-fil[iq].ptc.x) < 10 ) &&((float)Math.abs((float)yi -fil[iq].ptc.y))< 10 ){
				comment="";
				//iq trouve "+iq;
				trouve_deplacement=true;calcul_equip=false;
				fil[iq].deplaceq=true;
				iq_dep=iq;
				dx_pt_souris=-fil[iq].ptc.x +xi;
				dy_pt_souris=-fil[iq].ptc.y +yi;
				System.out.println(" *****************deplacement initial,iq "+iq);
			    }
			}
		    }
		}
	    }
	    if(trouve_deplacement){
		if(relachee){
		    System.out.println("&&&&&&&&&&&&&&&&&& iq_dep"+iq_dep);
		    trouve_deplacement=false;
		    if(plaque){
			for(iq=0;iq<nfils;iq++)
			    if(horizontale)
				calc_images(fil[iq],(int)(float)Math.round(fil[iq].ptc.y));
			    else
				calc_images(fil[iq],(int)(float)Math.round(fil[iq].ptc.x));
			calculs();
			calculs_faits=true;command="";fin_gerelesmenus_avec_souris=true;
			calcul_equip=true;
		    }else{
			calc_images(fil[iq_dep],(int)(float)Math.round(fil[iq_dep].ptc.y));
			calculs();
			calculs_faits=true;command="";fin_gerelesmenus_avec_souris=true;
			for(iq=0;iq<nfils;iq++){
			    fil[iq].deplaceq=false;
			    fil[iq].ptc.print("fil "+iq+ "fil[iq].ptc");
			}
			calcul_equip=true;
		    }
		}else if(draguee){
		    System.out.println("draguee "+iq_dep);
		    dessine_plans();
		    for (iq=0 ;iq<nfils;iq++)
			fil[iq].dessine_fil();
		    //paint();
		    //if(relachee||draguee){
		    if(plaque){
			resultat_glisser_plans(ip_dep,xi,yi);
		    }else{
			fil[iq_dep].ptc.y=yi-dy_pt_souris;
			fil[iq_dep].ptc.x=xi-dx_pt_souris;
			fil[iq_dep].f.pnt.assigne(fil[iq_dep].ptc.x,fil[iq_dep].ptc.y);
		    }  
		    // }
		}
	    }
	    return ret;
	}
	void remplis_point_champ(int x,int y, boolean calcul_norme){
	    point chp=champ(x,y);point pnt=new point(x,y);
	    e_pt[n_pt_chp].remplis(chp,pnt);
	    n_pt_chp++;
	    if(calcul_norme){
		norme_de_champ1=e_pt[n_pt_chp-1].chp.longueur();
		if(norme_de_champ1>norme_de_champ)
		    norme_de_champ=norme_de_champ1;
		if(n_pt_chp/20*20==n_pt_chp)
		    System.out.println(" n_pt_chp "+n_pt_chp+" x "+x+" y "+y+" norme_de_champ "+(float)norme_de_champ+" norme_de_champ1 "+(float)norme_de_champ1);
	    }
	}
	public point champ(int x,int y){         
	    point elec=new point((float)0.,(float)0.);
	    for( int iq=0;iq<  nfils;iq++){ 
		point dr=new point(x-fil[iq].ptc.x,y-fil[iq].ptc.y);
		float drcar=dr.carre();
		if(drcar>(float)0.)
		    elec.additionne_facteur(dr,fil[iq].qc/drcar);
		//	    {System.out.println(x,y,ex,ey,image);}
		//				for image=2 to nimage do
		int ifact=1;
		for (int image=1 ; image <nimage ;image ++){
		    //  System.out.println("image"+image);
		    ifact=-ifact;
		    for(int k=0;k<2;k++){
			point dr1;
			if(horizontale)
			    dr1=new point(x-fil[iq].ptc.x,y-fil[iq].yim[image][k]);
			else
			    dr1=new point(x-fil[iq].xim[image][k],y-fil[iq].ptc.y);
			elec.additionne_facteur(dr1,fil[iq].qc/dr1.carre()*ifact);
		    }
		}
	    }
	    elec.multiplie_cst( pixels_par_metre/ (deux_pi_eps0));
	    return elec;
	}
	public point champ_print(int x,int y){         
	    point elec=new point((float)0.,(float)0.);
	    for( int iq=0;iq<  nfils;iq++){ 
		point dr=new point(x-fil[iq].ptc.x,y-fil[iq].ptc.y);
		elec.additionne_facteur(dr,fil[iq].qc/dr.carre());
		elec.print("iq "+iq+" x "+x+" y "+y+" fil[iq].qc "+fil[iq].qc+" elec ");
		dr.print("fil[iq].ptc.x "+fil[iq].ptc.x+" fil[iq].ptc.y "+fil[iq].ptc.y+ " dr");
		//				for image=2 to nimage do
		int ifact=1;
		for (int image=1 ; image <nimage ;image ++){
		    ifact=-ifact;
		    for(int k=0;k<2;k++){
			point dr1;
			if(horizontale)
			    dr1=new point(x-fil[iq].ptc.x,y-fil[iq].yim[image][k]);
			else
			    dr1=new point(x-fil[iq].xim[image][k],y-fil[iq].ptc.y);
			elec.additionne_facteur(dr1,fil[iq].qc/dr1.carre()*ifact);
			elec.print("k "+k+" image "+image+"xim[image][k] "+fil[iq].xim[image][k]+" elec ");		    
		    }
		}
	    }
	    elec.multiplie_cst(pixels_par_metre/ (deux_pi_eps0));
	    return elec;
	}
	public point champ_iqprime(float x,float y,int iqprime,int i_chp_suppl){         
	    point elec=new point((float)0.,(float)0.);
	    point dr=new point(x-fil[iqprime].ptc.x,y-fil[iqprime].ptc.y);
	    elec.additionne_facteur(dr,fil[iqprime].qc/dr.carre());
	    if(i_chp_suppl==1){
		if(iessai<20)elec.print("dr.carre() "+dr.carre()+" fil[iqprime].qc "+fil[iqprime].qc+"elec ");
		float direction=fil[iqprime].e_suppl.chp.direction();
		if(dr.carre()<(float)4.){
		    float p=((float)(dr.direction()-direction))/((float)(subject.pi/(float)4.));
		    if(p<0)p+=(float)8.;if(p>(float)7.)p-=(float)8.;
		    int i=(int)p;
		    float dp=p-i;
		    int j=i+1;if(j>7)j-=8;
		    float dsig=fil[iqprime].sigma_suppl[j]-fil[iqprime].sigma_suppl[i];
		    float sig=fil[iqprime].sigma_suppl[i]+dsig*dp;
		    //on suppose le champ en 1/r a partir de la valeur initiale; le dr.carre() dans la ligne suivante se decompose en fait en deux dr.longueur() 
		    float facteur=sig*fil[iqprime].qc/pixels_par_metre* (2*subject.pi )/dr.carre();
		    elec.additionne_facteur(dr,facteur);
		    if((iessai<50)&&(iqprime==0))
			elec.print("i "+i+" p "+p+" dp "+dp+" dr.carre "+dr.carre()+" elec ");
		}else{
		    for(int i=0;i<8;i++){
			float dir=direction+i*subject.pi/(float)4.;
			point toto=new point((float)Math.cos(dir),(float)Math.sin(dir));
			toto.multiplie_cst(fil[iqprime].rc/pixels_par_metre);
			point drr=new point(x-fil[iqprime].ptc.x-toto.x,y-fil[iqprime].ptc.y-toto.y);
			float d=fil[iqprime].rc*fil[iqprime].qc*((float)2.*subject.pi/(float)8.);
			elec.additionne_facteur(drr,fil[iqprime].sigma_suppl[i]*d/drr.carre());
			if((iessai<50)&&(iqprime==0)){
			    elec.print("i "+i+"d "+d+" drr.carre "+drr.carre()+" elec ");
			    iessai++;
			}
		    }
		}
	    }
	    int ifact=1;
	    for (int image=1 ; image <nimage ;image ++){
		//  System.out.println("image"+image);
		ifact=-ifact;
		for(int k=0;k<2;k++){
		    point dr1;
		    if(horizontale)
			dr1=new point(x-fil[iqprime].ptc.x,y-fil[iqprime].yim[image][k]);
		    else
			dr1=new point(x-fil[iqprime].xim[image][k],y-fil[iqprime].ptc.y);
		    elec.additionne_facteur(dr1,fil[iqprime].qc/dr1.carre()*ifact);
		}
	    }
	    elec.multiplie_cst(pixels_par_metre/ deux_pi_eps0);
	    return elec;
	}
	public void calculs(){
	    System.out.println(" Calcul des densites de charge sur les fils");
	    point chp;
	    matrice mat_v_q= new matrice(nfils);float secmem[]=new float[32];
	    for(int iq=0;iq<nfils;iq++)
		fil[iq].qc=(float)1.;
	    for(int i_chp_suppl=0;i_chp_suppl<2;i_chp_suppl++){
		if(i_chp_suppl==1){
		    for(int iq=0;iq<nfils;iq++){
		        chp=champ_des_autres_au_centre_fils(iq);
			fil[iq].e_suppl.chp.assigne(chp);
			for(int i=0;i<8;i++){
			    fil[iq].sigma_suppl[i]=((float)2.*subject.eps0*chp.longueur()*(float)Math.cos(chp.direction()+i*subject.pi/(float)4.))/fil[iq].qc;
			    System.out.println(" iq "+iq+" fil[iq].sigma_suppl[i] "+fil[iq].sigma_suppl[i]);
			}
		    }
		    for(int iq=0;iq<nfils;iq++)
			fil[iq].qc=(float)1.;
		}
		for (int iq=0;iq<nfils;iq++){
		    if(!fil[iq].sense)
			secmem[iq]=v_champ;
		    else
			secmem[iq]=v_sensible;
		    int iq_min=0;
		    if(symetrique)
			iq_min=iq;
		    System.out.println("symetrique "+symetrique);
		    for (int iqprime=iq_min;iqprime<nfils;iqprime++){
			if(!(symetrique&&iq==iqprime&&iq!=0))
			    mat_v_q.directe[iq][iqprime]=circulation(iq,iqprime,i_chp_suppl);
			if(symetrique)
			    mat_v_q.directe[iqprime][iq]=mat_v_q.directe[iq][iqprime];
		    }
		}
		if(symetrique)
		    for (int iq=1;iq<nfils;iq++){
			mat_v_q.directe[iq][iq]=mat_v_q.directe[0][0];
			//System.out.println("iq "+iq+" mat_v_q.directe[iq][iq] "+mat_v_q.directe[iq][iq]);
		    }
		mat_v_q.invers ();
		for (int iq=0;iq<  nfils;iq++){
		    fil[iq].qc=0;
		    for (int iqprime=0;iqprime<  nfils;iqprime++)
			fil[iq].qc +=mat_v_q.inverse[iq][iqprime]*secmem[iqprime];
		    System.out.println(" iq "+iq+" secmem[iq] "+secmem[iq]+" fil[iq].qc "+fil[iq].qc);
		}
			     /*
			       for(int iq=0;iq<nfils;iq++)
			       chp=champ_des_autres_au_centre_fils(iq);
			     */
	    }
	    calculs_faits=true;
	}
	public  void paint(){
	    n_paint++;
	    System.out.println("debut paint painting "+painting+" n_paint "+n_paint);
	    ecrire_bandeau();
	    //if(n_paint>3)
	    //kammer[103]=null;
	    if(!painting){
		System.out.println("pt_supplementaire"+pt_supplementaire);
		painting=true;
		boolean pott_aallternes=false;
		for(int i=0;i<nfils;i++)
		    if(!fil[i].sense)
			pott_aallternes=true;
		boolean refaire_equipot=false;
		if(pt_supplementaire){
		    float norme_de_champ=(float)0.0;
		    for ( int i=0;i<n_pt_chp;i++){
			float norme_de_champ1=e_pt[i].chp.longueur();
			if( norme_de_champ1>norme_de_champ){
			    norme_de_champ=norme_de_champ1;
			    if((i==n_pt_chp-1)&&(n_pt_chp!=1)){
				refaire_equipot=true;
				if(!montrer_cosmiques)
				    calcul_equip=true;
			    }
			}
		    }
		}
		System.out.println("refaire_equipot"+refaire_equipot);
		if((!show_forces)&&((!pt_supplementaire)||refaire_equipot)){
		    System.out.println("&&i_chambre "+i_chambre+" i_ens "+i_ens);
		    dessine_plans();
		    System.out.println("i_chambre "+i_chambre+" i_ens "+i_ens);
		    String str="v="+(int)v_sensible+"Volts";
		    grp_ensemble.setColor(Color.red);
		    if(i_demarre==-1)
			grp_ensemble.drawString(str,right_ens_cyl/2-120,top_ens_cyl+150);
		    if(i_demarre==0)
			grp_ensemble.drawString(str,right_ens_cyl-120,top_ens_cyl+150);
		    if(i_demarre==1)
			grp_ensemble.drawString(str,right_ens_cyl1-240,top_ens_cyl1+100);
		    str="v_champ="+(int)v_champ+"Volts";
		    if(pott_aallternes){
			grp_ensemble.setColor(Color.black);
			if(i_demarre==-1)
			    grp_ensemble.drawString(str,right_ens_cyl/2-120,top_ens_cyl+200);
			if(i_demarre==0)
			    grp_ensemble.drawString(str,right_ens_cyl-120,top_ens_cyl+200);
			if(i_demarre==1)
			    grp_ensemble.drawString(str,right_ens_cyl1-240,top_ens_cyl1+120);
		    }   
		    grp_ensemble.setFont(subject.times14);
		    System.out.println(" avant dessine fils ");
		    for (int iq=0 ;iq< nfils;iq++)
			fil[iq].dessine_fil();	
		    System.out.println(" apres dessine fils ");
		    if (!calculs_faits)
			calculs();
		    for (int iq=0 ;iq< nfils;iq++){ 
			str=""+fil[iq].charge_simple ();
			if(fil[iq].sense){
			    if(i_demarre<=1){
				grp_ensemble.setColor(Color.red);
				if(horizontale)
				    grp_ensemble.drawString(str,(int)(float)Math.round(fil[iq].ptc.x),(int)(float)Math.round(fil[iq].ptc.y)-10);
				else
				    grp_ensemble.drawString(str,(int)(float)Math.round(fil[iq].ptc.x+10),(int)(float)Math.round(fil[iq].ptc.y));
			    }
			}else{
			    if(i_demarre<=1){
				grp_ensemble.setColor(Color.black);
				if(horizontale)
				    grp_ensemble.drawString(str,(int)(float)Math.round(fil[iq].ptc.x),(int)(float)Math.round(fil[iq].ptc.y)-10);
				else
				    grp_ensemble.drawString(str,(int)(float)Math.round(fil[iq].ptc.x+10),(int)(float)Math.round(fil[iq].ptc.y));
			    }
			}
		    }
		    if(calcul_equip)
			   calcul_equipot();
		    System.out.println("n_pt_chp "+n_pt_chp+" norme_de_champ "+norme_de_champ );
		    for ( int i=0;i<n_pt_chp;i++){
			norme_de_champ1=e_pt[n_pt_chp].chp.longueur();
			if( norme_de_champ1>norme_de_champ)
			    norme_de_champ=norme_de_champ1;
		    }
		    System.out.println(" norme_de_champ "+norme_de_champ );
		    for (int i=0;i<n_pt_chp;i++)
			e_pt[i].dessine((float)50.0 /norme_de_champ,(float)1.,Color.cyan);
		}else{
		    if(show_forces){
			if (! calculs_faits)calculs();
			float norme_de_force1,norme_de_force=(float)0.;
			for (int iq=0 ;iq< nfils;iq++){
			    chp=champ_des_autres_au_centre_fils(iq);
			    fil[iq].e_suppl.chp.assigne(chp);
			    fil[iq].f.chp.assigne(fil[iq].e_suppl.chp);
			    fil[iq].f.chp.multiplie_cst(fil[iq].qc);
			}
			norme_de_force=(float)0.;
			for (int iq=0 ;iq< nfils;iq++){
			    norme_de_force1=fil[iq].f.chp.longueur();
			    if(norme_de_force1>norme_de_force)norme_de_force=norme_de_force1;
			    fil[iq].f.chp.print("norme_de_force"+norme_de_force+"norme_de_force1 "+norme_de_force1+" fil[iq].f.chp ");
			}
			for (int iq=0 ;iq< nfils;iq++)
			    fil[iq].f.dessine((float)50.0/norme_de_force,(float)1.,Color.blue);
		    }else if(pt_supplementaire){
			System.out.println("avant dessine_champs");
			for (int i=0;i<n_pt_chp;i++)
			    e_pt[i].dessine((float)50.0/norme_de_champ,(float)1.,Color.magenta);
			System.out.println("apres dessine_champs");
		    }
		}
	    }
	    painting=false;
	    System.out.println(" fin paint i_chambre"+i_chambre+" painting "+painting);
	}
	public point champ_des_autres_au_centre_fils( int kfil){         
	    point elec=new point((float)0.,(float)0.);
	    float x=fil[kfil].ptc.x;
	    float y=fil[kfil].ptc.y;
	    point dr=new point((float)0.,(float)0.);
	    point dr1=new point((float)0.,(float)0.);
	    for( int iq=0;iq<nfils;iq++){ 
		if(iq!=kfil){
		    dr.assigne_soustrait(fil[kfil].ptc,fil[iq].ptc);
		    elec.additionne_facteur(dr,fil[iq].qc/dr.carre());
		}
		int ifact=1;
		for (int image=1 ; image <nimage ;image ++){
		    //  System.out.println("image"+image);
		    ifact=-ifact;
		    for(int k=0;k<2;k++){
			if(horizontale)
			    dr1.assigne(x-fil[iq].ptc.x,y-fil[iq].yim[image][k]);
			else
			    dr1.assigne(x-fil[iq].xim[image][k],y-fil[iq].ptc.y);
			elec.additionne_facteur(dr1,fil[iq].qc/dr1.carre()*ifact);
		    }
		}
	    }
	    elec.multiplie_cst(pixels_par_metre/deux_pi_eps0);
	    /*
	    float dir=elec.direction();
	    float chp_elec=(float)0.;
	    float facteur=fil[kfil].qc*(subject.pi/(float)4.)/deux_pi_eps0;
	    //if(kfil==0)System.out.println(" facteur "+facteur+" dir "+dir); 
	    for(int i=0;i<8;i++){
		chp_elec-=facteur*fil[kfil].sigma_suppl[i]*(float)Math.cos(dir+i*subject.pi/(float)4.);
		//if(kfil==0)elec.print("fil[kfil].sigma_suppl[i] "+fil[kfil].sigma_suppl[i]+" chp_elec "+chp_elec+" elec");
	    }
	    */		
	    return elec;
	}
	public void cree_un_fil(int h,int v,boolean sensse){
	    fil[nfils]=new fil_tendu(h,v,sensse);
	    nfils++;
	    symetrique=false;
	    calculs_faits=false;
	    if(horizontale)
		calc_images(fil[nfils-1],(int)(float)Math.round(fil[nfils-1].ptc.y));
	    else
		calc_images(fil[nfils-1],(int)(float)Math.round(fil[nfils-1].ptc.x));
	    
	    /*
	      calculs();calculs_faits=true;
	    */
	}
	class fil_tendu{
	    //	yimm=array[(float)1..25,(float)1..20,(float)1..2] of integer;
	    champ_en_un_point f;point ptc;
	    boolean equipotfaite[]= new boolean [5];;
	    float vdeb[]=new float[5];
	    float sigma_suppl[]=new float[8];
	    champ_en_un_point e_suppl;boolean sense;
	    point elec_deb[]=new point[5];
	    int ydeb[]=new int[5];int xdeb[]=new int[5];
	    int yim[][]=new int[10][2];int xim[][]=new int[10][2];
	    boolean deplaceq;
	    float qc;float rc;
	    fil_tendu(int posx,int posy,boolean sense1){
		qc=(float)1.;
		deplaceq=true;sense=sense1;
		ptc=new point((float)posx,(float)posy);
		f=new champ_en_un_point();
		f.pnt.assigne(ptc.x,ptc.y);
		rc=(float)50.e-6;
		for (int i=0 ;i< 5 ;i++){
		    equipotfaite[i]=false;
		    elec_deb[i]=new point((float)0.,(float)0.);
		}
		for (int i=0 ;i< 8 ;i++)
		    sigma_suppl[i]=(float)0.;
		e_suppl=new champ_en_un_point();
		e_suppl.pnt.assigne(ptc.x,ptc.y);
		e_suppl.chp=new point((float)0.,(float)0.);
	    }
	    void dessine_fil(){
		if(sense)
		    subject.paintcircle_couleur(grp_ensemble,(int)(float)Math.round(ptc.x),(int)(float)Math.round(ptc.y),6,Color.red);
		else
		    subject.paintcircle_couleur(grp_ensemble,(int)(float)Math.round(ptc.x),(int)(float)Math.round(ptc.y),6,Color.black);
	    }
	    public void deplace (float xi,float yi,int ppmouseh,int ppmousev ){
		int ii;float xep,yep,dx,dy;
		float xe=(float)0.,ye=(float)0.;
		if (deplaceq){
		    //	repeat
		    dx=-ptc.x+xi;
		    dy=-ptc.y+yi;
		    //getmouse(pp);
		    xep=xe;
		    yep=ye;
		    xe=ppmouseh-dx;
		    ye=ppmousev-dy;
		    if (((float)Math.abs(xep-xe) < 20)&&((float)Math.abs(yep-ye) < 20)){ 
			ptc.x=(int)(float)Math.round(ppmouseh-dx);
			ptc.y=(int)(float)Math.round(ppmousev-dy);
		    }
		}
	    }
	    public float charge_simple (){
		int q=(int)(float)Math.round(qc*10/(float)1.e-9);
		float qq=((float)q)/(float)10.0;
		return qq;
	    }
	}
	class champ_en_un_point{
	    point chp;point pnt;
	    public champ_en_un_point (){
		chp=new point((float)0.,(float)0.);
		pnt=new point((float)0.,(float)0.);
	    }
	    public void remplis(point v,point p){
		chp.assigne(v);
		pnt.assigne(p.x,p.y);
	    }
	    public void dessine( float fzoom,float fct_zm_sspl,Color couleur){
		float direction=chp.direction();
		
		int x_ini=(int)(float)Math.round(pnt.x); int y_ini=(int)(float)Math.round(pnt.y);
		int x_fin=x_ini+(int)(chp.x*fzoom*fct_zm_sspl);int y_fin=y_ini+(int)(chp.y*fzoom*fct_zm_sspl);
		grp_ensemble.setColor(couleur);
		//System.out.println(" x_ini "+x_ini+" y_ini "+y_ini+" x_fin "+x_fin+" y_fin "+y_fin);	    
		grp_ensemble.drawLine(x_ini,y_ini,x_fin,y_fin);
		float dir=direction+3*subject.pi/(float)4.;
		int xf1=x_fin+(int)((float)7.0*(float)Math.cos(dir));int yf1=y_fin+(int)((float)7.0*(float)Math.sin(dir));
		grp_ensemble.drawLine(x_fin,y_fin,xf1,yf1);
		dir=direction-3*subject.pi/(float)4.;
		xf1=x_fin+(int)((float)7.0*(float)Math.cos(dir));yf1=y_fin+(int)((float)7.0*(float)Math.sin(dir));
		grp_ensemble.drawLine(x_fin,y_fin,xf1,yf1);
	    }
	}
    }
    class chambre_a_fils_horizontale extends chambre {
	plans_de_masse plans;
	chambre_a_fils_horizontale(int i_chambre1,boolean pot_alternes1){
	    super(i_chambre1,pot_alternes1);
	    horizontale=true;
	    System.out.println("dans chambre_a_fils_uniforme i_chambre"+i_chambre+" i_demarre "+i_demarre);
	    if(i_demarre==-1){
		plans= new plans_de_masse(80,300);
		if(i_ens==0){
		    nfils=4;
		    System.out.println(" plans.yd[0]  "+plans.yd[0]+" plans.yd_init[0]  "+plans.yd_init[0]+" i_ens "+i_ens); 
		    for(int i=0;i<nfils;i++){
			int iaaa=100+80*i;int ibbb=170;
			fil[i]=new fil_tendu(iaaa,ibbb,true); 
		    }
		    fil[0].sense=false;
		    fil[nfils-1].sense=false;
		}else{
		    System.out.println(" plans.yd[0]  "+plans.yd[0]+" plans.yd_init[0]  "+plans.yd_init[0]+" i_ens "+i_ens);
		    nfils=5; 
		    for(int i=0;i<nfils;i++){
			int iaaa=75+70*i;int ibbb=170;
			fil[i]=new fil_tendu(iaaa,ibbb,i!=(i/2*2)); 
		    }
		}
	    }
	    if(i_demarre==0){
		plans= new plans_de_masse(100,300);
		if(i_ens==0){
		    nfils=6;
		    for(int i=0;i<nfils;i++){
			int iaaa=100+100*i;int ibbb=200;
			fil[i]=new fil_tendu(iaaa,ibbb,true); 
		    }
		    fil[0].sense=false;
		    fil[nfils-1].sense=false;
		}else{
		    nfils=7;
		    for(int i=0;i<nfils;i++){
			int iaaa=80+80*i;int ibbb=200;
			fil[i]=new fil_tendu(iaaa,ibbb,i!=(i/2*2)); 
		    }
		}
	    }
	    if(i_demarre==2){
		plans= new plans_de_masse(60+i_chambre*150,200+i_chambre*150);
		nfils=21;
		for(int i=0;i<nfils;i++){
		    int iaaa=40+30*i;int ibbb=130+i_chambre*150;
		    fil[i]=new fil_tendu(iaaa,ibbb,true); 
		}
		fil[0].sense=false;
		fil[nfils-1].sense=false;
	    }
	    if(i_demarre==3&&(i_chambre==0||i_chambre==2)){
		plans= new plans_de_masse(60+i_chambre*300,120+i_chambre*300);
		nfils=21;
		for(int i=0;i<nfils;i++){
		    int iaaa=140+20*i;int ibbb=90+i_chambre*300;
		    fil[i]=new fil_tendu(iaaa,ibbb,true); 
		}
		fil[0].sense=false;
		fil[nfils-1].sense=false;
	    }
	    for(int i=0;i<nfils;i++)
		calc_images(fil[i],(int)(float)Math.round(fil[i].ptc.y));
	}
	int plans_yd(int i_plan){
	    return plans.yd[i_plan];
	}
	public void calc_images(fil_tendu wire,int coord){
	    float ypl1,ypl2;
	    ypl1=plans.yd[0];ypl2=plans.yd[1];
	    wire.yim[0][0]=coord;wire.yim[0][1]=coord;
	    for (int image=1;image< nimage;image++){
		wire.yim[image][ 0]=(int)(ypl1-(wire.yim[image-1][0]- ypl1));
		wire.yim[image][1]=(int)(ypl2-(wire.yim[image-1][1]-ypl2));
		if (ypl1==plans.yd[0]) 
		    ypl1 =plans. yd[1];
		else
		    ypl1=plans.yd[0];
		if (ypl2==plans.yd[0]) 
		    ypl2=plans.yd[1];
		else
		    ypl2=plans.yd[0];
	    }
	}
	public boolean trouveequipot (int i,int ifil,point chp,point elecp,float v,float vp,int x,int y,int sens){
	    boolean trouve=false;int ypoint=0,xpoint=0;
	    
	    if (((v > vref)&&(vp <= vref)) || ((v < vref)&&(vp >= vref))){
		//System.out.println(" trouve equipot"+"v "+v+"vp "+vp+"vref "+vref); 
		trouve=true;
		if ((float)Math.abs(v-vref) > (float)Math.abs(vp-vref)){
		    ypoint=y-sens;
		    if (fixeequipot){
			fil[ifil].vdeb[i]=vp;
			fil[ifil].elec_deb[i].assigne(elecp);
		    }
		}else{
		    ypoint=y;
		    if (fixeequipot){
			fil[ifil].vdeb[i]=v;
			fil[ifil].elec_deb[i].assigne(chp);
		    }
		}
		if (fixeequipot)
		    fil[ifil].ydeb[i]=ypoint;
		else{
		    drawline_couleur(grp_ensemble,x,ypoint,x,ypoint,Color.green);
		    if(npt_equipot/8*8==npt_equipot)
			remplis_point_champ(x,ypoint,true);
		}
	    }
	    return trouve;
	}
	void initialise_point_in_pointf(){
	    float dx=fil[kammer[0].nfils-1].ptc.x-fil[0].ptc.x;
	    point_in =new point(fil[0].ptc.x+(float)Math.random()*dx,(float)plans.yd[0]);
	    point_f =new point(fil[n_chambres-1].ptc.x+(float)Math.random()*dx,(float)kammer[n_chambres-1].plans_yd(1));
	}
	public float circulation(int iq,int iqprime,int i_chp_suppl){
	    float kc; float ey1,dc=(float)0.;
	    int x=(int)(float)Math.round(fil[iq].ptc.x);
	    int yp=(int)(float)Math.round(fil[iq].ptc.y-1);
	    int y=plans.yd[0];
	    if (yp > y) 
		kc=-(float)1.;
	    else
		kc=(float)1.;
	    if(iq==iqprime)
		kc*=(float)0.1;
	    if (yp != y){
		float yr=yp;
		long nbpt=(int)((y-yp)/kc);
		if(iq==iqprime)
		    chp=champ_iqprime((float)x,yr,iqprime,i_chp_suppl);
		else
		    chp=champ_iqprime((float)x,yr,iqprime,0);
		ey1=chp.y;
		dc=(chp.y+ey1)/(float)2.0*kc;
		if(iq==iqprime){
		    for (int n=0;n<100;n++){
			yr=yr+kc;
			chp=champ_iqprime((float)x,yr,iqprime,i_chp_suppl);
			dc += (chp.y+ey1)/(float)2.0*kc;
			ey1=chp.y;
			if((n<20)&&(iq==0))chp.print("n "+n+" dc "+dc+" yr "+yr+" chp");
		    }
		    kc*=(float)10.;
		    nbpt=(int)((y-yr)/kc);  
		}
		for (int n=0;n<  nbpt;n++){
		    yr=yr+kc;
		    chp=champ_iqprime((float)x,yr,iqprime,0);
		    dc += (chp.y+ey1)/(float)2.0*kc;
		    ey1=chp.y;
		}
	    }
	    dc=dc/pixels_par_metre;
	    return dc;
	}
	public void calcul_equipot(){
	    int sensx,sensy,sensxprinc;boolean quartdetour;
	    equipoting=true;
	    v_sensible=(float)Math.abs(v_sensible);
	    fixeequipot=true;
	    int ifil=0;n_pt_chp=0;
	    System.out.println(" calcul_equipot,n_pt_chp "+n_pt_chp );
	    while (ifil<  nfils){
		System.out.println(" ifil "+ifil );
		int x=(int)(float)Math.round(fil[ifil].ptc.x);
		float v=(float)0.;
		float vprec=(float)0.;
		int y=plans.yd[0];
		chp=champ(x,y);
		point elecp=new point((float)0.,(float)0.);
		elecp.assigne(chp);	    
		int i=0;
		sensy=1;
		vref=v_sensible/(float)32.0;
		blok:
		while (i < 5){
		    //System.out.println(" i "+i );
		    fil[ifil].equipotfaite[i]=false;
		    if((!fil[ifil].sense)&&(vref>=v_champ-20))break blok;
		    y=y+1;
		    chp=champ(x,y);
		    vprec=v;
		    v=v-(chp.y+elecp.y)/(float)2.0 /pixels_par_metre ;
		    //if(y<100)System.out.println("v "+v+" ex "+ex+"ey "+ey+"x"+x+"y"+y+"vref"+vref+" fil[ifil].qc  "+fil[ifil].qc);
		    trouve=trouveequipot(i,ifil,chp,elecp,v,vprec,x,y,sensy);
		    elecp.assigne(chp);
		    if (trouve){
			vref=vref*(float)2.0;
			System.out.println(" i "+i+" ifil "+ifil+" x "+x+" y "+y+" vref "+vref+" v "+v);
			i++;
		    }
		}
		ifil++;
	    }
	    System.out.println(" niveau 1" );
	    
	    fixeequipot=false;
	    point elecpp=new point((float)0.,(float)0.);point elecppp=new point((float)0.,(float)0.);
	    ifil=0;
	    while (ifil<  nfils){
		vref=v_sensible/(float)64.0;
		for (int i=0 ;i< 5 ;i++){
		    npt_equipot=0;
		    //System.out.println("i"+i+"ifil"+ ifil+"fil[ifil].equipotfaite[i]"+fil[ifil].equipotfaite[i]);
		    
		    vref *= (float)2.0;
		    point elecp=new point((float)0.,(float)0.);
		    //System.out.println(" ifil "+ifil+" fil[ifil].ydeb[i] "+fil[ifil].ydeb[i]+" fil[ifil].vdeb[i] "+fil[ifil].vdeb[i]);
		    //boolean aaa=!(!fil[ifil].sense&&(vref>v_champ-20));
		    //System.out.println("aaa "+aaa+" fil[ifil].sense "+ fil[ifil].sense +" v_champ "+v_champ);
		    if((!fil[ifil].equipotfaite[i])&&!(!fil[ifil].sense&&(vref>v_champ-20))){
			sensxprinc=-3;
			while (sensxprinc < 0){
			    sensxprinc += 2;
			    int x=(int)(float)Math.round(fil[ifil].ptc.x);
			    ydepart=fil[ifil].ydeb[i];
			    vdepart=fil[ifil].vdeb[i];
			    sensx=sensxprinc;
			    quartdetour=false;
			    elecp.assigne(fil[ifil].elec_deb[i]);
			    float v=vdepart;
			    while ((x-fil[ifil].ptc.x)*sensxprinc >= 0){
				v=vdepart;
				int y=ydepart;
				x=x+sensx;
				chp=champ(x,y);
				v -= (chp.x +elecp.x)*sensx/(float)2.0 /pixels_par_metre;
				if ((v < vref)&&(chp.y <= 0) || (v > vref)&&(chp.y >= 0)) 
				    sensy=1;
				else
				    sensy=-1;
				if(quartdetour){
				    sensy=1;
				    quartdetour=false;
				}
				elecp.assigne(chp);
				
				bloc1:
				{
				    for (int iy=0;iy<=  100 ;iy++){
					y=y+sensy;
					elecpp.assigne(chp);
					chp=champ(x,y);
					float vp=v;
					v=v-(chp.y +elecp.y)*sensy/(float)2.0/pixels_par_metre;
					trouve=trouveequipot(i,ifil,chp,elecp,v,vp,x,y,sensy);
					if (trouve){
					    npt_equipot++;
					    ydepart=y;
					    vdepart=v;
					    elecp.assigne(elecpp);
					    elecppp.assigne(elecp);
					    break bloc1;
					}
					elecp.assigne(chp);
				    }
				    if(!quartdetour){//%%%%
					quartdetour=true;
					elecp.assigne(elecppp);
					//System.out.println(" v " +v+" x "+x+" y "+y+" vref "+vref);
					sensx=-sensx;
				    }//%%%%
				    for (int jfil=0;jfil<  nfils;jfil++){
					if ((jfil != ifil)&&((fil[jfil].ptc.x-x)*(fil[jfil].ptc.x-fil[ifil].ptc.x)) < 0) 
					    fil[jfil].equipotfaite[i]=true;
				    }
				}
			    }
			}
		    }
		}
		ifil++;
	    }
	    System.out.println(" fin calcul_equipot" );	
	    calcul_equip=false;	    equipoting=false;
	}
	void dessine_plans(){
	    String str="";
	    if(horizontale){
		int yy0=0,yy1=0;
		
		if(plans.yd[0]>plans.yd_init[0])
		    yy0=plans.yd_init[0]-2;
		else
		    yy0=plans.yd[0]-2;
		if(plans.yd[1]<plans.yd_init[1])
		    yy1=plans.yd_init[1]+2;
		else
		    yy1=plans.yd[1]+2;
		subject.eraserect(grp_ensemble,yy0,20,yy1,770,Color.white);
		subject.paintrect(grp_ensemble,plans.yd[0]-2,20,plans.yd[0],770);
		subject.paintrect(grp_ensemble,plans.yd[1],20,plans.yd[1]+2,770);
		//plans.yd_init[0]=plans.yd[0];
		//plans.yd_init[1]=plans.yd[1];
		if(i_demarre<=0){
		    grp_ensemble.setColor(Color.blue);
		    str="Q,nCb/m";
		    grp_ensemble.drawString(str,(int)(float)Math.round(fil[0].ptc.x)-60,(int)(float)Math.round(fil[0].ptc.y)-10);
		    grp_ensemble.setColor(Color.black);
		    str="v=0Volt";
		    if(i_demarre!=-1){
			grp_ensemble.drawString(str,550,plans.yd[0]+15);
			grp_ensemble.drawString(str,550,plans.yd[1]-15);
		    }else{
			grp_ensemble.drawString(str,275,plans.yd[0]+15);
			grp_ensemble.drawString(str,275,plans.yd[1]-15);
		    }
		}
	    }
	}
	boolean teste_souris(int x,int y){
	    return (y > plans.yd[0])&&(y <plans.yd[1]);
	}
	public boolean glisser_plan(){
	    for(int ip=0;ip<2;ip++){
		if ((float)Math.abs((float)ppmousev -plans.yd[ip])< 7 ){
		    comment="";
		    calcul_equip=false;
		    ip_dep=ip;
		    dy_pt_souris=-plans.yd[ip] +ppmousev;
		    //System.out.println(" *****************deplacement initial,ip "+ip);
		    return true;
		}
	    }
	    return false;
	}
	void resultat_glisser_plans(int ip_dep,int xi,int yi){
	    plans.yd[ip_dep]=yi-(int)dy_pt_souris;
	}
    	class plans_de_masse{ 
	    int yd[]= new int[2];
	    int yd_init[]= new int[2];
	    boolean deplace_plan;float yep,dy,ye;
	    plans_de_masse(int a1,int a2){
		yd[0]=a1;	yd[1]=a2;
		yd_init[0]=a1;	yd_init[1]=a2;
		deplace_plan=false;
	    }
	    
	    public void deplace ( float xi,float yi,int ii,int ppmouseh,int ppmousev){
		//repeat
		dy=-yd[ii]+yi;
		//getmouse(pp);
		yep=ye;
		ye=ppmousev-dy;
		//	    until ! button;
		//getmouse(pp);
		yd[ii]=(int)(float)Math.round(ppmousev-dy);
	    }
	}
    }


    class chambre_a_fils_verticale extends chambre {
	plans_de_masse plans;
	chambre_a_fils_verticale(int i_chambre1,boolean pot_alternes1){
	    super(i_chambre1,pot_alternes1);
	    i_chambre=i_chambre1;
	    horizontale=false;
	    if(i_demarre==1){
		plans= new plans_de_masse(40,460);
		nfils=21;
		for(int i=0;i<nfils;i++){
		    int iaaa=240;int ibbb=180+20*i;
		    if(i_ens==0)
			fil[i]=new fil_tendu(iaaa,ibbb,true);
		    else
			fil[i]=new fil_tendu(iaaa,ibbb,i!=(i/2*2));
		}
		fil[0].sense=false;
		fil[nfils-1].sense=false;
	    }
	    if(i_demarre==3&&i_chambre==1){
		    plans= new plans_de_masse(60,720);
		    nfils=21;
		    for(int i=0;i<nfils;i++){
			int iaaa=350;int ibbb=180+20*i;
			fil[i]=new fil_tendu(iaaa,ibbb,i!=(i/2*2)); 
		    }
	    }
	    System.out.println("dans chambre_a_fils i_chambre"+i_chambre+" i_demarre "+i_demarre);
	    for(int i=0;i<nfils;i++)
		calc_images(fil[i],(int)(float)Math.round(fil[i].ptc.x));
	}
	public void calc_images(fil_tendu wire,int coord){
	    float xpl1,xpl2;
	    xpl1=plans.xd[0];xpl2=plans.xd[1];
	    wire.xim[0][0]=coord; wire.xim[0][1]=coord;
	    for (int image=1;image< nimage;image++){
		wire.xim[image][0]=(int)(xpl1-(wire.xim[image-1][0]- xpl1));
		wire.xim[image][1]=(int)(xpl2-(wire.xim[image-1][1]-xpl2));
		if (xpl1==plans.xd[0]) 
		    xpl1 =plans. xd[1];
		else
		    xpl1=plans.xd[0];
		if (xpl2==plans.xd[0]) 
		    xpl2=plans.xd[1];
		else
		    xpl2=plans.xd[0];
	    }
	}
	public boolean trouveequipot (int i,int ifil,point chp,point elecp,float v,float vp,int x,int y,int sens){
	    boolean trouve=false;int ypoint=0,xpoint=0;
	    
	    if (((v > vref)&&(vp <= vref)) || ((v < vref)&&(vp >= vref))){
		//System.out.println(" trouve equipot"+"v "+v+"vp "+vp+"vref "+vref); 
		trouve=true;
		if ((float)Math.abs(v-vref) > (float)Math.abs(vp-vref)){
		    xpoint=x-sens ;
		    if (fixeequipot){
			fil[ifil].vdeb[i]=vp;
			fil[ifil].elec_deb[i].assigne(elecp);
		    }
		}else{
		    xpoint=x;
		    if (fixeequipot){
			fil[ifil].vdeb[i]=v;
			fil[ifil].elec_deb[i].assigne(chp);
		    }
		}
		if (fixeequipot)
		    fil[ifil].xdeb[i]=xpoint;
		else{
		    drawline_couleur(grp_ensemble,xpoint,y,xpoint,y,Color.green);
		    if(npt_equipot/8*8==npt_equipot)
			remplis_point_champ(xpoint,y,true);
		}
	    }
	    return trouve;
	}
	int plans_yd(int i_plan){
	    return 0;
	}

	void resultat_glisser_plans(int ip_dep,int xi,int yi){
	    plans.xd[ip_dep]=xi-(int)dx_pt_souris;
	}
	void initialise_point_in_pointf(){
	    float dx=(float)plans.xd[1]-(float)plans.xd[0];
	    point_in =new point((float)plans.xd[0]+(float)Math.random()*dx,(float)130.0);
	    point_f =new point((float)plans.xd[0]+(float)Math.random()*dx,(float)650.);
	    point_in.print("point_in ");
	    point_f.print("point_f ");
	}
	public float circulation( int iq,int iqprime,int i_chp_suppl){
	    int y=(int)(float)Math.round(fil[iq].ptc.y);
	    int xp=(int)(float)Math.round(fil[iq].ptc.x-1);
	    int x=plans.xd[0];
	    float kc;float ex1,dc=(float)0.;
	    if (xp > x) 
		kc=(float)-1.;
	    else
		kc=(float)1.;
	    if(iq==iqprime)
		kc*=(float)0.1;
	    
	    if (xp != x){
		float xr=xp;
		long nbpt=(int)((x-xp)/kc);
		//System.out.println(" nbpt "+nbpt+" kc "+kc+" x "+x+" xp "+xp);
		if(iq==iqprime)
		    chp=champ_iqprime(xr,(float)y,iqprime,i_chp_suppl);
		else
		    chp=champ_iqprime(xr,(float)y,iqprime,0);
		ex1=chp.x;
		dc=(chp.x+ex1)/(float)2.0*kc;
		if(iq==iqprime){
		    for (int n=0;n<100;n++){
			xr=xr+kc;
			chp=champ_iqprime(xr,(float)y,iqprime,i_chp_suppl);
			dc += (chp.x+ex1)/(float)2.0*kc;
			ex1=chp.x;
		    }
		    kc*=(float)10.;
		    nbpt=(int)((x-xr)/kc);  
		}
		for (int n=0;n<  nbpt;n++){
		    xr=xr+kc;
		    chp=champ_iqprime(xr,(float)y,iqprime,0);
		    dc += (chp.x+ex1)/(float)2.0*kc;
		    ex1=chp.x;
		    //		    if((iqprime==iq+1)&&(n==0))
		    //	chp.print("n "+n+" xr "+xr+" y "+y+" dc "+dc+" chp ");
		}
	    }
	    dc=dc/pixels_par_metre;
	    return dc;
	}
	public void calcul_equipot(){
	    int sensx,sensy,sensyprinc;boolean quartdetour;
	    equipoting=true;
	    v_sensible=(float)Math.abs(v_sensible);
	    fixeequipot=true;n_pt_chp=0;
	    System.out.println(" calcul_equipot,n_pt_chp "+n_pt_chp );
	    int ifil=0;
	    while (ifil<  nfils){
		int y=(int)(float)Math.round(fil[ifil].ptc.y);
		float v=(float)0.;
		float vprec=(float)0.;
		int x=plans.xd[0];
		chp=champ(x,y);
		point elecp=new point((float)0.,(float)0.);
		elecp.assigne(chp);	
		//chp.print("ifil "+ifil+" x "+x+" y "+y+ " chp");
		int i=0;
		sensx=1;
		vref=v_sensible/(float)32.0;
		int n=0;
		blok:
		while (i < 5){
		    fil[ifil].equipotfaite[i]=false;
		    if(!fil[ifil].sense&&(vref>=v_champ-20))break blok;
		    x=x+1;
		    n++;
		    chp=champ(x,y);
		    vprec=v;
		    v=v -(chp.x+elecp.x)/(float)2.0 /pixels_par_metre ;
		    //if((ifil==0)&&(n<100))chp.print("vprec "+vprec+" v "+ v+" x "+ x+" chp ");
		    trouve=trouveequipot(i,ifil,chp,elecp,v,vprec,x,y,sensx);
		    elecp.assigne(chp);
		    if (trouve){
			if(ifil==0)System.out.println(" i "+i+" fil[ifil].xdeb[i] "+fil[ifil].xdeb[i]+" fil[ifil].vdeb[i] "+fil[ifil].vdeb[i]);		
			vref=vref*(float)2.0;
			i++;
		    }
		}
		ifil++;
	    }
	    //System.out.println(" niveau 1" );
	    
	    fixeequipot=false;
	    point elecpp=new point((float)0.,(float)0.);point elecppp=new point((float)0.,(float)0.);
	    ifil=0;
	    while (ifil<  nfils){
		vref=v_sensible/(float)64.0;
		for (int i=0 ;i< 5 ;i++){
		    //System.out.println("i"+i+"ifil"+ ifil+"fil[ifil].equipotfaite[i]"+fil[ifil].equipotfaite[i]);
		    npt_equipot=0;
		    
		    vref *= (float)2.0;
		    point elecp=new point((float)0.,(float)0.);
		    System.out.println(" ifil "+ifil+" vref "+vref);
		    
		    if((!fil[ifil].equipotfaite[i])&&!(!fil[ifil].sense&&(vref>v_champ-20))){
			sensyprinc=-3;
			while (sensyprinc < 0){
			    sensyprinc += 2;
			    int y=(int)(float)Math.round(fil[ifil].ptc.y);
			    xdepart=fil[ifil].xdeb[i];
			    vdepart=fil[ifil].vdeb[i];
			    sensy=sensyprinc;
			    quartdetour=false;
			    elecp.assigne(fil[ifil].elec_deb[i]);
			    float v=vdepart;
			    int n=0;
			    while ((y-fil[ifil].ptc.y)*sensyprinc >= 0){
				v=vdepart;
				int x=xdepart;
				y=y+sensy;
				chp=champ(x,y);
				//if((n<10)&&(n>0))System.out.println(" v " +v+" x "+x+" y "+y);
				v -= (chp.y +elecp.y)*sensy/(float)2.0 /pixels_par_metre;
				if((n<10)&&(n>0))System.out.println("&&& v " +v+" x "+x+" y "+y);
				if ((v < vref)&&(chp.x <= 0) || (v > vref)&&(chp.x >= 0)) 
				    sensx=1;
				else
				    sensx=-1;
				if(quartdetour){
				    sensx=1;
				    quartdetour=false;
				}
				elecp.assigne(chp);
				
				bloc1:
				{
				    for (int ix=0;ix<=  100 ;ix++){
					x=x+sensx;
					elecpp.assigne(chp);
					chp=champ(x,y);
					float vp=v;
					v=v-(chp.x +elecp.x)*sensx/(float)2.0/pixels_par_metre;
					trouve=trouveequipot(i,ifil,chp,elecp,v,vp,x,y,sensx);
					//if((n<10)&&(n>0))System.out.println(" v " +v+" x "+x+" y "+y+" trouve "+trouve);
					if (trouve){
					    npt_equipot++;
					    xdepart=x;
					    vdepart=v;
					    if((n<10))System.out.println(" v " +v+" x "+x+" y "+y+" trouve "+trouve);
					    elecp.assigne(elecpp);
					    elecppp.assigne(elecp);
					    break bloc1;
					}
					elecp.assigne(chp);
				    }
				    if(!quartdetour){//%%%%
					quartdetour=true;
					elecp.assigne(elecppp);
					n++;
					//if(n<10)System.out.println("****** v " +v+" x "+x+" y "+y);
					sensy=-sensy;
				    }//%%%%
				    for (int jfil=0;jfil<  nfils;jfil++){
					if ((jfil != ifil)&&((fil[jfil].ptc.y-y)*(fil[jfil].ptc.y-fil[ifil].ptc.y)) < 0) 
					    fil[jfil].equipotfaite[i]=true;
				    }
				}
			    }
			}
		    }
		}
		ifil++;
	    }
	    System.out.println(" fin calcul_equipot" );	
	    calcul_equip=false;equipoting=false;
	}
	void dessine_plans(){
	    String str="";
	    int xx0=0,xx1=0;
	    if(plans.xd[0]>plans.xd_init[0])
		xx0=plans.xd_init[0]-2;
	    else
		xx0=plans.xd[0]-2;
	    if(plans.xd[1]<plans.xd_init[1])
		xx1=plans.xd_init[1]+2;
	    else
		xx1=plans.xd[1]+2;
	    if(i_demarre==3){
		subject.eraserect(grp_ensemble,130,xx0,650,xx1,Color.white);
		subject.paintrect(grp_ensemble,130,plans.xd[0]-2,650,plans.xd[0]);
		subject.paintrect(grp_ensemble,130,plans.xd[1],650,plans.xd[1]+2);
	    }else{
		subject.eraserect(grp_ensemble,30,xx0,800,xx1,Color.white);
		subject.paintrect(grp_ensemble,30,plans.xd[0]-2,800,plans.xd[0]);
		subject.paintrect(grp_ensemble,30,plans.xd[1],800,plans.xd[1]+2);
	    }
	    if(i_demarre==1){
		grp_ensemble.setColor(Color.blue);
		str="Q,nCb/m";
		grp_ensemble.drawString(str,(int)(float)Math.round(fil[0].ptc.x+10),(int)(float)Math.round(fil[0].ptc.y)-20);
		str="v=0Volt";
		grp_ensemble.drawString(str,plans.xd[0]+15,550);
		grp_ensemble.drawString(str,plans.xd[1]-25,550);
	    }
	}
	boolean teste_souris(int x,int y){
	    return (x > plans.xd[0])&&(x < plans.xd[1]);
	}
	public boolean glisser_plan(){
	    for(int ip=0;ip<2;ip++){
		if ((float)Math.abs((float)ppmouseh -plans.xd[ip])< 7 ){
		    comment="";
		    calcul_equip=false;
		    ip_dep=ip;
		    dx_pt_souris=-plans.xd[ip]+ ppmouseh;
		    //System.out.println(" *****************deplacement initial,ip "+ip);
		    return true;
		}
	    }
	    return false;
	}
    	class plans_de_masse{ 
	    int xd[]= new int[2];
	    int xd_init[]= new int[2];
	    boolean deplace_plan;float xep=(float)0.,dx=(float)0.,xe=(float)0.;
	    plans_de_masse(int a1,int a2){
		xd[0]=a1;	xd[1]=a2;
		xd_init[0]=a1;	xd_init[1]=a2;
		deplace_plan=false;
	    }
	    
	    public void deplace ( float xi,float yi,int ii,int ppmouseh,int ppmousev){
		//repeat
		dx=-xd[ii]+xi;
		//getmouse(pp);
		xep=xe;
		xe=ppmousev-dx;
		//	    until ! button;
		//getmouse(pp);
		xd[ii]=(int)(float)Math.round(ppmousev-dx);
	    }
	}
    }
}

class matrice{
    float directe[][]=new float [32][32];
    float essai[][]=new float [32][32];
    float inverse[][]=new float [32][32];
    int dim;
    public matrice(int dim1){
	dim=dim1;
    }
    public void somme_lignes(){
	for (int k=0;k<dim;k++){
	    float somme=(float)0.;
	    for (int j=0;j<dim;j++)
		somme+=directe[k][j];
	    System.out.println("k "+k+" somme "+somme);
	}
    }
    public void invers(){
	float pivot,souspiv;
	for (int i=0;i<dim;i++)
	    for (int j=0;j<dim;j++){
		essai[i][j]=directe[i][j]; 
		if (i==j)
		    inverse[i][j]=(float)1.0;
		else
		    inverse[i][j]=(float)0.0;
	    }
	for (int i=0;i<dim;i++){
	    pivot=essai[i][i];
	    for (int j=i;j<dim;j++)
		essai[i][j]=essai[i][j]/pivot;
	    for (int j=0;j<=i;j++)
		inverse[i][j]=inverse[i][j]/pivot;
	    //System.out.println("i "+i+" pivot "+pivot+" essai[i][i] "+essai[i][i]);
	    if(i>26)
		for (int j=i;j<dim;j++)
		    System.out.println("j "+j+" "+essai[j][24]+" "+essai[j][25]+" "+essai[j][26]+" "+essai[j][27]+" "+essai[j][28]+" "+essai[j][29]+" "+essai[j][30]+" "+essai[j][31]);
	    for (int ii=0;ii<dim;ii++){
		if (ii!=i) {
		    souspiv=essai[ii][i];
		    for (int j=i;j<dim;j++)
			essai[ii][j]-=essai[i][j]*souspiv;
		    for (int j=0;j<=i;j++)
			inverse[ii][j]-=inverse[i][j]*souspiv;
		}
	    }
	    //if(i>26)
	    //	for (j=i;j<dim;j++)
	    //    System.out.println("***j "+j+" "+essai[j][24]+" "+essai[j][25]+" "+essai[j][26]+" "+essai[j][27]+" "+essai[j][28]+" "+essai[j][29]+" "+essai[j][30]+" "+essai[j][31]);
	    
	}
	System.out.println(" 7 termes diagonaux de la matrice inverse ");
	System.out.println( inverse[0][ 0]+" "+ inverse[1][ 1]+" "+ inverse[2][ 2]+" "+ inverse[3][ 3]+" "+ inverse[4][ 4]+" "+ inverse[5][ 5]+" "+ inverse[6][ 6]+" "+ inverse[7][ 7]);
	/*
	  for (k=0;k<dim;k++){
	  for (j=0;j<dim;j++){
	  essai[k][j]=0.;
	  for (int l=0;l<dim;l++)
	  essai[k][j]+=directe[k][l]*inverse[l][j];
	  }
	  System.out.println("k "+k+" "+essai[k][0]+" "+essai[k][1]+" "+essai[k][2]+" "+essai[k][3]+" "+essai[k][4]+" "+essai[k][5]+" "+essai[k][6]+" "+essai[k][7]);
	  }
	*/
    }
}
class point{
    float x,y;    
    static final float pi=(float)3.141592652;
    public point(float xi,float yi){
	x=xi;y=yi;
    }
    public point(int xi,int yi){
	x=(float)xi;y=(float)yi;
    }
    public point(point a){
	x=a.x;y=a.y;
    }
    public void assigne(float xi,float yi){
	x=xi;y=yi;
    }
    public void assigne(point a){
	x=a.x;y=a.y;
    }
    public void assigne_soustrait(point a,point b){
	x=a.x-b.x;y=a.y-b.y;
    }
    public float distance_carre(point pt){
	float d;
	d=(float)Math.pow(x-pt.x,2)+(float)Math.pow(y-pt.y,2);
	return d;
    }
    public float carre(){
	return((float)Math.pow(x,2)+(float)Math.pow(y,2));
    }
    public float distance(point pt){
	float d;
	d=(float)Math.sqrt((float)Math.pow(x-pt.x,2)+(float)Math.pow(y-pt.y,2));
	return d;
    }
    public void additionne_point_facteur(point a,float c){
	x+=c*a.x;
	y+=c*a.y;
    }
    public float scalaire(point c){
	return(x*c.x+y*c.y);
    }
    public float longueur(){
	return((float)Math.sqrt((float)Math.pow(x,2)+(float)Math.pow(y,2)));
    }
    public float direction(){
	float direction=(float)0.;
	if((float)Math.abs(x)>(float)Math.abs(y)){
	    direction=(float)Math.asin(y/longueur());
	    if(x<(float)0.)
		if(y>(float)0.)
		    direction=pi-direction;
		else
		    direction=-pi-direction;
	}else{
	    direction=(float)Math.acos(x/longueur());
	    if(y<(float)0.)direction=-direction;
	}
	return direction;
    }
    public void additionne_facteur_print(point a,float c){
	a.print(" x "+x+" y "+y+"  a ");
	x+=c*a.x;
	y+=c*a.y;
	a.print(" c "+c+" x "+x+" y "+y+"  a ");
    }
    public void additionne_facteur(point a,float c){
	x+=c*a.x;
	y+=c*a.y;
    }
    public void multiplie_cst(float a){
	x*=a;
	y*=a;
    }
    
    public void projections(float cosinus,float sinus){
	float x_p=x;float y_p=y;
	x=-sinus*x_p+cosinus*y_p;
	y=cosinus*x_p+sinus*y_p;
    }
    public void zero(){
	x=(float)0.;y=(float)0.;
    }
    public void print(String st){
	System.out.println(st+ " x "+(float)x+" y "+(float)y);
    }
}


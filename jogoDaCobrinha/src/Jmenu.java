import resources.Sound;
import view.Window;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

public class Jmenu extends  JFrame implements  KeyListener{

    BufferedImage backBuffer;
    int FPS = 30;
    int janela_W = 1080;
    int janela_H = 720;

    /* Declarando o menu Com:
    * 4 itens, coordenadas x e y = 100, ativo = true
    * olhe o método abaixo cenarios() */
    Menu menu_principal = new Menu(3, (janela_W / 2) - 50, (janela_H / 2) - 50, true);


    /* Método que vai desenhar na tela alguns possíveis cenários do game
    * lá em Menu.java cenário foi definido como -1
    * se cenário == 0 muda a cor do fundo e mostra m texto
    * se cenário == n muda a cor do fundo e mostra um texto*/

    //Obter o método desenhar gráficos
    public void cenarios(){
        Graphics bbg = backBuffer.getGraphics();
        bbg.setFont(new Font("Arial", Font.BOLD, 20));
        if(menu_principal.cenario == 0){
            Sound.START.play();
            bbg.setColor(new Color(100, 255, 193));
            bbg.fillRect(0, 0, janela_W, janela_H);
            bbg.setColor(Color.BLACK);
            bbg.drawString("Jogar ", 100, 200);
            menu_principal.cenario = -1;
            new Window();
            this.dispose();
            // aqui você pode escolher o que irá aparecer caso o usuário escolha este item do menu
        }
        if(menu_principal.cenario == 1){
            bbg.setColor((new Color(234, 100, 255)));
            bbg.fillRect(0, 0, janela_W, janela_H);
            bbg.setColor(Color.BLACK);
            bbg.drawString("Sobre o jogo:", 100, 200);
            bbg.drawString("O jogo da cobrinha, também conhecido como \"Snake\", é um clássico dos jogos eletrônicos que teve", 100, 250);
            bbg.drawString(" origem em jogos simples desenvolvidos nas décadas de 1970 e 1980.", 100,280);
        }
        if(menu_principal.cenario == 2){
            Sound.START.play();
            System.exit(0); // esse comando fecha o game
        }
    }

    public void atualizar(){
    }

    //---------------------------------------- FUNÇÕES MENU PRINCIPAL ------------------------------------------------//

    public void desenhar_Graficos(){
        Graphics g = getGraphics();
        Graphics bbg = backBuffer.getGraphics();
        bbg.setColor(Color.cyan);
        bbg.fillRect(0, 0, janela_W, janela_W); // Pinta o fundo com um quadrado da cor selecionada

        menu_principal.desenhar_menu(); // isso desenhará o menu
        cenarios(); // isso irá desenhar os cenários escolhidos no menu

        g.drawImage(backBuffer, 0, 0, this); // Isso deve ficar sempre no final
    }

    //Método inicializar()
    public void inicializar(){
        setTitle("Snake Game");
        setSize(janela_W, janela_H);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setVisible(true);
        backBuffer = new BufferedImage(janela_W, janela_H, BufferedImage.TYPE_INT_RGB);

        // adicionar um escutador de teclas
        addKeyListener(this);

        // definindo o texto de cada item do menu
        menu_principal.itens[0] = "Jogar";
        menu_principal.itens[1] = "Sobre";
        menu_principal.itens[2] = "Sair";
        menu_principal.bbg = backBuffer.getGraphics();
    }

    // Run Menu
    public void run(){
        inicializar();
        while (true){
            atualizar();
            desenhar_Graficos();
            try {
                Thread.sleep(1000 / FPS);
            } catch (Exception e){
                System.out.println("Thread interrompida");
            }
        }
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyPressed(KeyEvent e) {
        // aqui chamamos os métodos que irá controlar o menu pelo teclado
        menu_principal.controlar(e); // controla o menu
        menu_principal.voltar_ao_menu(e); // faz voltar para o menu quando pressionamos "Esc."
    }

    public void keyReleased(KeyEvent e) {
    }
}

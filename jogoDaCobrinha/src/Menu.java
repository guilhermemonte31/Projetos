import resources.Sound;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.concurrent.TimeUnit;

public class Menu {
    int cenario = -1; // Indicar em que cenário estamos, -1(nenhum) (0,1,2,3... n) outros cenários
    int item_selecionado = 0; // Indicar qual item está selecionado (atual é zero, pois é o primeiro item do menu)
    String[]    itens; // Guardar os itens do menu (jogar, sobre, sair... etc)
    Graphics bbg; // Desenhar elementos na tela
    boolean ativo; // indica se o menu está ativo ou não (ao iniciar o jogo, desativamos o menu, para o controlar durante o jogo)
    int x; // coordenada x do menu
    int y; // coordenada y do menu
    int tamanho_da_fonte = 20;
    int distancia_entre_itens = 15; // distância entre cada item do menu
    Font fonte = new Font("Biome", Font.BOLD, tamanho_da_fonte); // fonte do menu
    Color cor_selecionada = new Color(255, 0, 0); // Cor vermelha para o item selecionado
    Color cor_nao_selecionada = new Color(0, 0 , 0); // Cor preta para o item que não está selecionado

    // Construtor ao criar o objeto do tipo menu, informar o número de itens , as coordenadas e se ele estará ativo ou não
    public Menu(int numero_de_itens, int x, int y, boolean ativo){
        itens = new String[numero_de_itens];
        this.x = x;
        this.y = y;
        this.ativo = ativo;
    }

    // Método que só chamará o método controlaMenu se ele estiver ativo
    public void controlar(KeyEvent e){
        if(ativo){
            controlar_Menu(e);

        }
    }

    // Retornar ao menu ao pressionar "Esc"
    public void voltar_ao_menu(KeyEvent e){
        // Se a tecla pressionada for igual a "Esc"
        if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            cenario = -1; // sai dos cenários em que estivermos
            ativo = true; // reativa o menu
        }
    }

    // Método para controlar o menu através do celadon
    // chamado dentro do método controla()
    private void controlar_Menu(KeyEvent e) {
        // se pressionar a tecla "cima" diminui 1 em item_selecionado
        if(e.getKeyCode() == KeyEvent.VK_UP){
            item_selecionado -= 1;
        }
        // se pressionar a tecla "baixo" aumenta 1 em item_selecionado
        if(e.getKeyCode() == KeyEvent.VK_DOWN){
            item_selecionado += 1;
        }
        // agora para controlar os itens extremos do menu
        // caso eu esteja com 1º item selecionado e pressione para cima, ele vai para a última opção
        // caso eu esteja com o último item selecionado e pressione para baixo, ele vai para primeira opção
        if(item_selecionado >= itens.length){
            item_selecionado = 0;
        }
        if(item_selecionado < 0){
            item_selecionado = itens.length - 1;
        }
        // se pressionar a tecla Enter ele muda o valor de cenário para o ‘item’ selecionado
        // isso fará mudar de cenário e desativará o menu para que ele não seja mais controlado
        if(e.getKeyCode() == KeyEvent.VK_ENTER){
            cenario = item_selecionado;
            ativo = false;
        }
    }

    /* Método que irá desenhar o menu na tela
     um for que irá listar todos os itens guardados em itens[]
     um cálculo é feito para a coordenada y de cada item do menu, para que eles fiquem
     um distante do outro, então no caso y = 10, o item 0 será: 10+(0*(20+15)) = 10
     para o item 1 será: 10+(1*(20+15)) = 45
     para o item 2 será: 10+(2*(20+15)) = 80
     e assim por diante...*/
    public void desenhar_menu(){
        bbg.setFont(fonte); // seta a fonte que foi definida na declaração de variáveis
        for(int i = 0; i < itens.length; i++){
            if(item_selecionado == i){ // se ele estiver selecionado muda a cor pra vermelho e desenha o item na tela
                bbg.setColor(cor_selecionada);
                bbg.drawString(itens[i], x, y+(i*(tamanho_da_fonte + distancia_entre_itens)));
        } else{ // se não estiver selecionado, muda a cor para preto e desenha o item na tela
                bbg.setColor(cor_nao_selecionada);
                bbg.drawString(itens[i], x, y+(i*(tamanho_da_fonte + distancia_entre_itens)));
            }
        }
    }
}

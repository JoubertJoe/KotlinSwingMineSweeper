package visao

import modelo.Tabuleiro
import modelo.TabuleiroEvento
import java.awt.BorderLayout
import java.awt.Color
import java.awt.FlowLayout
import java.awt.GridLayout
import java.awt.event.KeyEvent
import javax.swing.*
import javax.swing.border.EmptyBorder
import javax.swing.text.StyleConstants.setBackground
import javax.swing.JPanel
import javax.swing.JFrame
import javax.swing.JOptionPane






class MenuEscolha : JFrame(){

    init {


      //  background = Color.WHITE
        defaultCloseOperation = EXIT_ON_CLOSE
        setSize(320,440)
        title = "Menu Principal"

        var contentPane = JPanel()
        contentPane.background = Color.WHITE
        contentPane.setBorder(EmptyBorder(5, 5, 5, 5))
        contentPane.layout = BorderLayout(0, 0)
        contentPane = contentPane
        add(contentPane)

        val lblCampoMinadoKotlin = JLabel ("CAMPO MINADO KOTLIN")
        lblCampoMinadoKotlin.setHorizontalAlignment(SwingConstants.CENTER)
        contentPane.add(lblCampoMinadoKotlin, BorderLayout.NORTH)

        val panelPersonalizado = JPanel()
        panelPersonalizado.setBackground(Color.WHITE)
        contentPane.add(panelPersonalizado, BorderLayout.SOUTH)
        panelPersonalizado.setLayout(FlowLayout(FlowLayout.CENTER, 5, 5))

        val lblLinhas = JLabel("Linhas:")
        panelPersonalizado.add(lblLinhas)

        val spnColuna = JSpinner()
        spnColuna.value = 10
        panelPersonalizado.add(spnColuna)

        val lbllinhas = JLabel("Linhas")
        panelPersonalizado.add(lbllinhas)

        val spnLinha = JSpinner()
        panelPersonalizado.add(spnLinha)
        spnLinha.value = 10

        val lblMinas = JLabel("Minas")
        panelPersonalizado.add(lblMinas)

        val spnMinas = JSpinner()
        panelPersonalizado.add(spnMinas)
        spnMinas.value = 10

        val panelBotoes = JPanel()
        panelBotoes.setBackground(Color.WHITE)
        contentPane.add(panelBotoes, BorderLayout.CENTER)
        panelBotoes.setLayout(GridLayout(0, 1, 0, 0))

        val verticalGlue_3 = Box.createVerticalGlue()
        panelBotoes.add(verticalGlue_3)

        val btnFcil = JButton("Fácil")
        btnFcil.setBackground(Color.WHITE);
        panelBotoes.add(btnFcil)
        btnFcil.addActionListener{
            linhas = 15
            colunas = 15
            minas = 10
            this.dispose()
            TelaPrincipal()
        }

        val verticalGlue = Box.createVerticalGlue();
        panelBotoes.add(verticalGlue);

        val btnMdio = JButton("Médio")
        btnMdio.setBackground(Color.WHITE)
        panelBotoes.add(btnMdio);
        btnMdio.addActionListener{
            linhas = 25
            colunas = 25
            minas = 50
            this.dispose()
            TelaPrincipal()
        }


        val verticalGlue_1 = Box.createVerticalGlue();
        panelBotoes.add(verticalGlue_1);

        val btnDifcil = JButton("Difícil");
        btnDifcil.setBackground(Color.WHITE);
        panelBotoes.add(btnDifcil);
        btnDifcil.addActionListener{
            linhas = 30
            colunas = 30
            minas = 100
            this.dispose()
            TelaPrincipal()
        }

        val verticalGlue_2 = Box.createVerticalGlue();
        panelBotoes.add(verticalGlue_2);

        val btnPersonalizado = JButton("Personalizado");
        btnPersonalizado.setBackground(Color.WHITE);
        panelBotoes.add(btnPersonalizado);
        btnPersonalizado.addActionListener{
            linhas = spnLinha.value as Int
            colunas = spnColuna.value as Int
            minas = spnMinas.value as Int
            TelaPrincipal()
        }

        isVisible = true
    }

}

var linhas = 0;
var colunas = 0;
var minas = 0;


class TelaPrincipal: JFrame(){
    private val tabuleiro = Tabuleiro(qtdLinhas = linhas, qtdColunas = colunas, qtdMinas = minas)
    private val painelTabuleiro = PainelTabuleiro(tabuleiro)


    init {
        tabuleiro.onEvento(this:: mostrarResultado)
        add(painelTabuleiro)
        val iconMina = ImageIcon("../img/bandeira.jpg")

        setSize(690,438) //ajustar tamanho se necessário
        setLocationRelativeTo(null)
        defaultCloseOperation = EXIT_ON_CLOSE
        title = "Campo Minado"
        isVisible = true
    }

    private fun mostrarResultado(evento: TabuleiroEvento){
        SwingUtilities.invokeLater {
            val msg = when(evento){
                TabuleiroEvento.VITORIA-> "Você ganhou! ☻"
                TabuleiroEvento.DERROTA-> "Você perdeu! ☠"
            }

            val options = arrayOf<Any>("Reiniciar", "Voltar ao menu")
            var opcao = JOptionPane.showOptionDialog(
                this,
                msg,
                "Fim de jogo!",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.WARNING_MESSAGE,
                null,
                options,
                options[0]
            )


            if (opcao == 0 || opcao == -1) {

                tabuleiro.reiniciar()

                painelTabuleiro.repaint()
                painelTabuleiro.validate()

            }else{
                this.dispose()
                MenuEscolha()

        }

        }
    }


}


fun main(args: Array<String>) {

     MenuEscolha()
}
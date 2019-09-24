package visao

import modelo.Tabuleiro
import java.awt.Color
import java.awt.GridLayout
import java.awt.BorderLayout
import javax.swing.border.EmptyBorder
import javax.swing.text.StyleConstants.setBackground
import java.awt.FlowLayout
import javax.swing.*
import javax.swing.text.StyleConstants.setBackground
import javax.swing.text.StyleConstants.setBackground



class PainelTabuleiro(tabuleiro: Tabuleiro): JPanel(){

    init {
        layout = GridLayout(tabuleiro.qtdLinhas, tabuleiro.qtdColunas)
        tabuleiro.forEachCampo {
            campo ->
            val botao = BotaoCampo(campo)
            add(botao)
        }
    }
}
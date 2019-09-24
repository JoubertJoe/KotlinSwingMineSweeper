package visao

import java.awt.Color
import java.awt.Font
import javax.swing.BorderFactory
import javax.swing.JButton

import modelo.Campo
import modelo.CampoEvento
import javax.swing.ImageIcon
import javax.swing.SwingUtilities

private val COR_BG_NORMAL = Color(242,242,242)
private val COR_BG_MARCACAO = Color(8,184,255)
private val COR_BG_EXPLOSAO = Color(235,85,85)
private val COR_TXT_VERDE = Color(0,180,0)
class BotaoCampo(private val campo: Campo): JButton(){

    init {
        font = font.deriveFont(Font.BOLD)
        background = COR_BG_NORMAL
        isOpaque = true
        border = BorderFactory.createBevelBorder(0)
        addMouseListener(MouseCliqueListener(campo, {it.abrir()}, {it.alterarMarcacao()}))
        campo.onEvento(this:: aplicarEstilo)
    }

    private fun aplicarEstilo(campo: Campo, evento: CampoEvento){
        when(evento){
            CampoEvento.EXPLOSAO -> aplicarEstiloExplodido()
            CampoEvento.ABERTURA -> aplicarEstiloAberto()
            CampoEvento.MARCACAO -> aplicarEstiloMarcado()
            else -> aplicarEstiloPadrao()
        }

        SwingUtilities.invokeLater {
            repaint()
            validate()
        }
    }

    private fun aplicarEstiloExplodido(){
        background = COR_BG_EXPLOSAO
        text = "*"
    }

    private fun aplicarEstiloAberto(){
        background = Color(190,190,190)
        border = BorderFactory.createLineBorder(Color.BLACK)

        foreground = when(campo.qtdVizinhosMinados){
            1-> COR_TXT_VERDE
            2-> Color.BLUE
            3-> Color.YELLOW
            4,5,6-> Color.RED
            else-> Color.PINK
        }

        text = if (campo.qtdVizinhosMinados > 0) campo.qtdVizinhosMinados.toString() else ""
    }

    private fun aplicarEstiloMarcado(){
        background = COR_BG_MARCACAO
        foreground = Color.BLACK
        text = "✔"
    }

    private fun aplicarEstiloPadrao(){
        background = COR_BG_NORMAL
        border = BorderFactory.createBevelBorder(0)
        text = ""
    }
}
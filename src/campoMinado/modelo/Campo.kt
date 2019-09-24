package modelo

import javax.security.auth.callback.Callback

enum class CampoEvento{
    ABERTURA,
    MARCACAO,
    DESMARCACAO,
    EXPLOSAO,
    REINICIALIZACAO
}

data class Campo(val linha: Int, val coluna: Int){
    private val vizinhos = ArrayList<Campo>()

    //Lista de eventos que será disparada pelo campo. Não retornará nada, por isso Unit
    private val callbacks = ArrayList<(Campo, CampoEvento) -> Unit>()

    var marcado: Boolean = false
    var aberto: Boolean = false
    var minado: Boolean = false

    //Somente leitura
    val desmarcado: Boolean get() = !marcado
    val fechado: Boolean get() = !aberto
    val seguro: Boolean get() = !minado
    val objetivoAlcancado: Boolean get() = seguro && aberto || minado && marcado
    val qtdVizinhosMinados: Int get() = vizinhos.filter { it.minado }.size

    val vizinhancaSegura: Boolean get() = vizinhos.map {
        it.seguro
    }.reduce{
        resultado, seguro -> resultado && seguro
    }

    /*
    Método que recebe um campo e adiciona na lista de vizinhos
    * */
    fun addVizinho(vizinho: Campo){
        vizinhos.add(vizinho)
    }
    /*Método que recebe o campo e o evento para o campo e adicionar na lista de callbacks
    * */
    fun onEvento(callback: (Campo, CampoEvento) -> Unit){
        callbacks.add(callback)
    }

    /*
        Se o usuário clicar em um botão para abrir, se ele estiver fechado ele será aberto.
     */
    fun abrir(){
        if (fechado){
            aberto = true

            if (minado){
                callbacks.forEach {
                    it(this, CampoEvento.EXPLOSAO)
                }
            }else{
                callbacks.forEach {
                    it(this, CampoEvento.ABERTURA)
                }

                vizinhos.filter {
                    it.fechado && it.seguro && vizinhancaSegura
                }.forEach {
                    it.abrir()
                }
            }
        }
    }

    fun alterarMarcacao(){

        if (fechado){
            marcado = !marcado

            val evento = if (marcado) CampoEvento.MARCACAO else CampoEvento.DESMARCACAO
            callbacks.forEach {
                it(this, evento)
            }
        }
    }

    fun minar(){
        minado = true
    }

    fun reiniciar(){
        aberto = false
        minado = false
        marcado = false
        callbacks.forEach {
            it(this, CampoEvento.REINICIALIZACAO)
        }
    }
}
# ED_TP
Trabalho prático de ED

# Objetivos:

- Gestão de Portals e Connectors:
    *   Adicionar, editar e remover Portals e Connectors.
    *   Para os Connectors, deverá ser possível adicionar e remover registos de interações que
      os jogadores realizam. É necessário armazenar a última interação de cada jogador.
      o Listar os Portals/Connectors vários critérios (por exemplo, ordenados por um
      determinado parâmetro).
    *   Deve ser possível importar/exportar um ficheiro JSON com toda informação relativa a
      Portals/Connectors.
- Gestão de rotas:
    * Criar e remover rotas entre Portals e Connectors.
    * Deve ser possível importar/exportar um ficheiro JSON com toda informação relativa às
      rotas existentes e os Portals/Connectors de interesse associados.
- Gestão de jogadores:
    * Adicionar, atualizar ou remover jogadores.
    * Associar e desassociar jogadores a equipas.
    * Listar os jogadores e por equipa, por nível e por número de Portals atualmente
      conquistados.
    * Deve ser possível importar/exportar um ficheiro JSON com toda informação relativa aos
      jogadores existentes assim como de estatísticas relacionadas (ver ponto anterior).
- Gestão de jogo:
    * Calcular o caminho mais curto entre dois pontos (Portals e/ou Connectors).
    * Calcular o caminho mais curto considerando a necessidade de passar obrigatoriamente
      por locais para recarregar ou passar obrigatoriamente apenas por Portals e Connectors
      (se possível).
    * Deve ser possível exportar um ficheiro JSON com toda informação relativa aos caminhos
      mais curtos gerados indicando a informação de cada Portals/Connectors envolvidos.
    * Deve ser possível importar/exportar um ficheiro JSON com as configurações do jogo.
# Implementação: 
  * Deverá implementar um programa que permita testar a API. O programa deve disponibilizar um menu
    para interagir com as diversas funcionalidades da API. O programa deverá:
    * Iterativamente identificar o jogador e pedir a movimentação que pretende efetuar no “mapa” de
      jogo, validando os caminhos possíveis para que o jogador possa realizar a movimentação.
    * Quando um jogador está posicionado num Connector, poderá ou não realizar o carregamento
      instantâneo da energia do jogador. Se o jogador voltar a acionar a mesma opção (antes do
      cooldown do Connector) o Conector deverá rejeitar a interação. Após o cooldown, o Connector
      fica recarregado para aquele jogador. O cooldown é aplicado a cada jogador individualmente.
    * Quando um jogador está posicionado num Portal:
      * Pode ou não conquistar o Portal se estiver em modo neutro. 
      * Pode ou não atacar o Portal, que resulta no desconto da sua energia na energia do Portal.
         Se a energia não for suficiente para derrubar o Portal, o Portal ficará com a energia
         restante até que seja atacado/recarregado;
      * Pode ou não recarregar o Portal com uma quantidade de energia selecionada.


 

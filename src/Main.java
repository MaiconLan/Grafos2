import javax.swing.*;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        Grafo grafo = new Grafo();

        configuracoesIniciais(grafo);

        int opt = -1;

        while (opt != 0) {
            opt = Integer.parseInt(input(getMenu()));

            switch (opt) {
                case 1:
                    definirVertices(grafo);
                    break;

                case 2:
                    adicionarAresta(grafo);
                    break;

                case 3:
                    listaArestas(grafo);
                    break;

                case 4:
                    matrizAdjacencia(grafo);
                    break;

                default:
                    break;

            }
        }

    }

    private static void configuracoesIniciais(Grafo grafo) {
        String valorado = "";
        String orientado = "";

        while (valorado.isEmpty() && orientado.isEmpty()){
            valorado = input("Grafo é valorado? (S/N)").toLowerCase().replace(" ", "");
            orientado = input("Grafo é orientado? (S/N)").toLowerCase().replace(" ", "");
        }

        grafo.setValorado(valorado.equals("s"));
        grafo.setValorado(orientado.equals("s"));
    }

    public static String input(String mensagem){
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void output(String mensagem, String titulo) {
        JOptionPane.showMessageDialog(null, new JTextArea(mensagem), titulo, 1);
    }

    public static String getMenu(){
        return "Selecione uma opção" +
                "\n1 - Definir os Vértice" +
                "\n2 - Adicionar Arestas" +
                "\n3 - Lista de Arestas" +
                "\n4 - Matriz de Adjacência" +
                "\n0 - Sair";
    }

    public static void definirVertices(Grafo grafo) {
        String[] nomesVertices = input("Insira os vértices do grafo separados por vírgula. \nExemplo: A, B, C, D").toUpperCase().replace(" ", "").split(",");

        grafo.getVertices().clear();
        grafo.getArestas().clear();

        for(int i = 0; i < nomesVertices.length; i++){
            Integer idVertice = i+1;
            grafo.getVertices().add(new Vertice(idVertice, nomesVertices[i]));
        }
    }

    public static void adicionarAresta(Grafo grafo) {
        String verticeOrigem = "Escolha pelo código, o vértice de ORIGEM!\n";
        String verticeDestino = "Agora escolha pelo código, o vértice de DESTINO!\n";
        String verticesCadastrados = "";

        for (Vertice vertice : grafo.getVertices()) {
            verticesCadastrados += vertice.getIdVertice() + " - " + vertice.getNome() + "\n";
        }
        Vertice origem = null;
        Vertice destino = null;

        while (origem == null && destino == null) {
            origem = obterVertice(grafo, Integer.parseInt(input(verticeOrigem + verticesCadastrados)));
            destino = obterVertice(grafo, Integer.parseInt(input(verticeDestino + verticesCadastrados)));
        }

        int valorVertice = 0;

        if(grafo.isValorado())
            valorVertice = Integer.parseInt(input("Insira valor para a Aresta.").toUpperCase().replace(" ", ""));

        Aresta aresta = new Aresta(origem, destino, valorVertice);
        grafo.getArestas().add(aresta);
    }

    public static Vertice obterVertice(Grafo grafo, Integer idVertice){
        for (Vertice vertice : grafo.getVertices()) {
            if (vertice.getIdVertice().equals(idVertice))
                return vertice;
        }
        output("Vértice não encontrado.", "Erro");
        return null;
    }

    private static void matrizAdjacencia(Grafo grafo) {
        List<Vertice> vertices = grafo.getVertices();
        List<Aresta> arestas = grafo.getArestas();
        String resultado = "\t";

        for (Vertice vertice : vertices)
            resultado += vertice.getNome() + "\t";

        resultado+= "\n";

        for (int i = 0; i < vertices.size(); i++) {
            resultado += "\n" + vertices.get(i) + "\t";

            for (int j = 0; j < vertices.size(); j++) {
                Vertice origem = vertices.get(i);
                Vertice destino = vertices.get(j);

                boolean possuiArestaOrientada = false;
                boolean possuiArestaNaoOrientada = false;
                int valorComAresta = 1;
                int valorSemAresta = 0;

                Aresta orientada = new Aresta(origem, destino);
                possuiArestaOrientada = arestas.contains(orientada);

                if(!grafo.isOrientado()){
                    Aresta naoOrientada = new Aresta(origem, destino);
                    possuiArestaNaoOrientada = arestas.contains(naoOrientada);
                }

                if(grafo.isValorado()) {
                    valorSemAresta = 999999999;
                }

                resultado += possuiArestaOrientada || possuiArestaNaoOrientada ? 1 + "\t" : valorSemAresta + "\t";

                if(j == vertices.size() - 1)
                    resultado += "\n";
            }
        }

        output(resultado, "Matriz Adjacência");
    }

    public static void listaAdjacencia(Grafo grafo) {
        String msg = "Lista de Adjacência\n\n";
        for (Vertice vertice : grafo.getVertices()) {
            msg += vertice.getNome() + " ";
        }
        output(msg, "Lista de Adjacencias");
    }

    private static void listaArestas(Grafo grafo) {
        String listaArestas = "Lista de Arestas\n\n";

        for (Aresta aresta : grafo.getArestas()) {
            listaArestas += "[" + aresta.getOrigem() +      ", " + aresta.getDestino();
            listaArestas += grafo.isValorado() ?  ", " + aresta.getValor() + "]\n" : "]\n";
        }

        output(listaArestas, "Lista de Arestas");
    }
}

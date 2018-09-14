import javax.swing.*;
import java.awt.*;
import java.util.*;
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
                    adicionarArestaDireto(grafo);
                    break;

                case 4:
                    listaArestas(grafo);
                    break;

                case 5:
                    matrizAdjacencia(grafo);
                    break;

                case 6:
                    listaAdjacencia(grafo);
                    break;

                case 7:
                    if(grafo.isOrientado())
                        matrizIncidenciaOrientada(grafo);
                    else
                        matrizIncidenciaNaoOrientada(grafo);
                    break;

                default:
                    break;

            }
        }

    }

    private static String getConfiguracoes(Grafo grafo){
        String retorno = "Grafo: ";
        retorno += grafo.isOrientado() ? "Orientado" : "Não-Orientado";
        retorno += ", ";
        retorno += grafo.isValorado() ? "Valorador" : "Não-Valorado";
        return retorno + "\t";
    }

    private static void configuracoesIniciais(Grafo grafo) {
        String valorado = "";
        String orientado = "";

        while (valorado.isEmpty() && orientado.isEmpty()){
            valorado = input("Grafo é valorado? (S/N)").toLowerCase().replace(" ", "");
            orientado = input("Grafo é orientado? (S/N)").toLowerCase().replace(" ", "");
        }

        grafo.setValorado(valorado.equals("s"));
        grafo.setOrientado(orientado.equals("s"));
    }

    public static String input(String mensagem){
        return JOptionPane.showInputDialog(mensagem);
    }

    public static void output(String mensagem, String titulo) {
        JTextArea textArea = new JTextArea(mensagem);
        JOptionPane.showMessageDialog(null, textArea, titulo, 1);
    }

    public static String getMenu(){
        return "Selecione uma opção" +
                "\n1 - Definir os Vértices" +
                "\n2 - Adicionar Uma aresta" +
                "\n3 - Adicionar várias Arestas" +
                "\n---------------------------------" +
                "\n4 - Lista de Arestas" +
                "\n5 - Matriz de Adjacência" +
                "\n6 - Lista de Adjacência" +
                "\n7 - Matriz de Incidência" +
                "\n---------------------------------" +
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

    public static void adicionarArestaDireto(Grafo grafo) {
        String mensagem = "Insira as arestas pelo código ao lado esquerdo do vértice, utilizando vírgula entre origem e destino, \nalém de ponto e vírgula para separar os vértices, Conforme exemplo:\n";
        String exemplo = "(ORIGEM,DESTINO;ORIGEM,DESTINO) \n\n1,2;1,3;1,4\n\n";
        String verticesCadastrados = "";

        for (Vertice vertice : grafo.getVertices()) {
            verticesCadastrados += vertice.getIdVertice() + " - " + vertice.getNome() + "\n";
        }
        String input = input(mensagem + exemplo + verticesCadastrados);
        String[] arrayVertices = input.replace(" ", "").split(";");

       for (String vertice : arrayVertices) {
            String[] verticesOrigemDestino = vertice.split(",");
            Vertice origem = obterVertice(grafo, Integer.parseInt(verticesOrigemDestino[0]));
            Vertice destino = obterVertice(grafo, Integer.parseInt(verticesOrigemDestino[1]));


            int valorAresta = 0;

            if(grafo.isValorado())
                valorAresta = Integer.parseInt(input("Insira valor para a Aresta.").toUpperCase().replace(" ", ""));

            Aresta aresta = new Aresta(origem, destino, valorAresta, "e" + (grafo.getArestas().size()+1));
            grafo.getArestas().add(aresta);

            if(!grafo.isOrientado()) {
                Aresta arestaNaoOrientada = new Aresta(destino, origem, valorAresta, "e" + (grafo.getArestas().size()+1));
                grafo.getArestas().add(arestaNaoOrientada);
            }
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

        int valorAresta = 0;

        if(grafo.isValorado())
            valorAresta = Integer.parseInt(input("Insira valor para a Aresta.").toUpperCase().replace(" ", ""));

        Aresta aresta = new Aresta(origem, destino, valorAresta, "e" + (grafo.getArestas().size()+1));
        grafo.getArestas().add(aresta);

        if(!grafo.isOrientado()) {
            Aresta arestaNaoOrientada = new Aresta(destino, origem, valorAresta, "e" + (grafo.getArestas().size()+1));
            grafo.getArestas().add(arestaNaoOrientada);
        }
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
        String resultado = getConfiguracoes(grafo) + "\n\n\t";

        for (Vertice vertice : vertices)
            resultado += vertice.getNome() + "\t";

        resultado+= "\n";

        for (int i = 0; i < vertices.size(); i++) {
            resultado += "\n" + vertices.get(i) + "\t";

            for (int j = 0; j < vertices.size(); j++) {
                Vertice origem = vertices.get(i);
                Vertice destino = vertices.get(j);

                boolean possuiAresta = false;

                int valorAresta = grafo.isValorado() ? 999999999 : 0 ;

                Aresta orientada = new Aresta(origem, destino);
                possuiAresta = arestas.contains(orientada);

                if(possuiAresta)
                    valorAresta = grafo.isValorado() ? arestas.get(arestas.indexOf(orientada)).getValor() : 1;

                if(!grafo.isOrientado()){
                    Aresta naoOrientada = new Aresta(destino, origem);
                }

                resultado +=  valorAresta + "\t";

                if(j == vertices.size() - 1)
                    resultado += "\n";
            }
        }

        output(resultado, "Matriz Adjacência");
    }

    public static void listaAdjacencia(Grafo grafo) {
        String listaAdjacencia = getConfiguracoes(grafo) + "\n\n";
        List<Vertice> vertices = grafo.getVertices();
        Set<Aresta> arestas = new HashSet<>();

        if(grafo.isOrientado())
            arestas = new HashSet<>(grafo.getArestas());
        else
            arestas = getArestasSemVerticeOrigemDestinoAoContratio(grafo);

        for (Vertice vertice : vertices) {
            listaAdjacencia += vertice;
            for (Aresta aresta : arestas) {
                if(aresta.getOrigem().equals(vertice)) {
                    listaAdjacencia += " -> " + aresta.getDestino();
                }
            }
            listaAdjacencia += "-|\n";
        }
        output(listaAdjacencia, "Lista de Adjacencias");
    }

    public static Set<Aresta> getArestasSemVerticeOrigemDestinoAoContratio(Grafo grafo) {
        Set<Aresta> arestas = new HashSet<>();

        for (Aresta aresta : grafo.getArestas()) {
            Aresta arestaContraria = new Aresta(aresta.getDestino(), aresta.getOrigem());
            if(grafo.getArestas().contains(aresta) && grafo.getArestas().contains(arestaContraria))
                arestas.add(aresta);
        }

        return arestas;
    }

    public static Set<Aresta> getArestasSemVerticeDuplicados(Grafo grafo) {
        Set<Aresta> arestas = new HashSet<>();

        for (Aresta aresta : grafo.getArestas()) {
            Aresta arestaContraria = new Aresta(aresta.getDestino(), aresta.getOrigem());
            if(!(arestas.contains(aresta) || arestas.contains(arestaContraria))) {
                aresta.setNome("e"+(arestas.size()+1));
                arestas.add(aresta);
            }
        }

        return arestas;
    }

    private static void listaArestas(Grafo grafo) {
        String listaArestas = getConfiguracoes(grafo) + " \n\n";

        for (Aresta aresta : grafo.getArestas()) {
            listaArestas += "[" + aresta.getOrigem() + ", " + aresta.getDestino();
            listaArestas += grafo.isValorado() ?  ", " + aresta.getValor() + "]\n" : "]\n";
        }

        output(listaArestas, "Lista de Arestas");
    }

    private static void matrizIncidenciaNaoOrientada(Grafo grafo) {
        String matrizIncidencia = getConfiguracoes(grafo) + " \n\n\t";

        List<Vertice> vertices = grafo.getVertices();
        Set<Aresta> arestas = getArestasSemVerticeDuplicados(grafo);

        for (Aresta aresta : arestas)
            matrizIncidencia += aresta.getNome() + "\t";

        for (Vertice vertice : vertices) {
            matrizIncidencia += "\n" + vertice.getNome() + "\t";
            for (Aresta aresta : arestas){
                if(aresta.getOrigem().equals(vertice) || aresta.getDestino().equals(vertice))
                    matrizIncidencia += 1;
                else
                    matrizIncidencia += 0;
                matrizIncidencia += "\t";
            }
        }

        output(matrizIncidencia, "Matriz de Incidência");
    }


    private static void matrizIncidenciaOrientada(Grafo grafo) {
        String matrizIncidencia = getConfiguracoes(grafo) + " \n\n\t";

        List<Vertice> vertices = grafo.getVertices();
        Set<Aresta> arestas = getArestasSemVerticeDuplicados(grafo);

        for (Aresta aresta : arestas)
            matrizIncidencia += aresta.getNome() + "\t";

        for (Vertice vertice : vertices) {
            matrizIncidencia += "\n" + vertice.getNome() + "\t";
            for (Aresta aresta : arestas){
                if(aresta.getOrigem().equals(vertice))
                    matrizIncidencia += 1;
                else if(aresta.getDestino().equals(vertice))
                    matrizIncidencia += -1;
                else
                    matrizIncidencia += 0;
                matrizIncidencia += "\t";
            }
        }

        output(matrizIncidencia, "Matriz de Incidência");
    }
}

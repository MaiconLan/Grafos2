import java.util.Objects;

public class Vertice {

    private Integer idVertice;
    private String nome;

    public Vertice() {
    }

    public Vertice(String nome) {
        this.nome = nome;
    }

    public Vertice(Integer idVertice, String nome) {
        this.idVertice = idVertice;
        this.nome = nome;
    }

    public Integer getIdVertice() {
        return idVertice;
    }

    public void setIdVertice(Integer idVertice) {
        this.idVertice = idVertice;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vertice vertice = (Vertice) o;
        return Objects.equals(idVertice, vertice.idVertice) &&
                Objects.equals(nome, vertice.nome);
    }

    @Override
    public int hashCode() {
        return Objects.hash(idVertice, nome);
    }
}

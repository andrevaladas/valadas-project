package com.master.ed;

/**
 * @author André Valadas
 * @serial STIF - Pneus Inspeções
 * @serialsince 06/2012
 */
public class Stif_Pneu_InspecaoED extends RelatorioBaseED {

	private static final long serialVersionUID = -2113404527113415723L;

	private long oid_Pneu_Inspecao;
	private long oid_Veiculo_Inspecao;
	private String dm_Posicao;
	private long nr_Eixo;
	private long nr_Fogo;
	private String dm_Vida_Pneu;
	private double nr_Mm_Sulco;
	private long nr_Pressao; 
	private long oid_Pneu_Dimensao; 
	private long oid_Fabricante_Pneu; 
	private long oid_Modelo_Pneu; 
	private long oid_Fabricante_Banda; 
	private long oid_Banda; 

	//List<Stif_Problema_PneuED>
	private transient String array;

	public long getOid_Pneu_Inspecao() {
		return oid_Pneu_Inspecao;
	}
	public void setOid_Pneu_Inspecao(long oidPneuInspecao) {
		oid_Pneu_Inspecao = oidPneuInspecao;
	}
	public long getOid_Veiculo_Inspecao() {
		return oid_Veiculo_Inspecao;
	}
	public void setOid_Veiculo_Inspecao(long oidVeiculoInspecao) {
		oid_Veiculo_Inspecao = oidVeiculoInspecao;
	}
	public String getDm_Posicao() {
		return dm_Posicao;
	}
	public void setDm_Posicao(String dmPosicao) {
		dm_Posicao = dmPosicao;
	}
	public long getNr_Eixo() {
		return nr_Eixo;
	}
	public void setNr_Eixo(long nrEixo) {
		nr_Eixo = nrEixo;
	}
	public long getNr_Fogo() {
		return nr_Fogo;
	}
	public void setNr_Fogo(long nrFogo) {
		nr_Fogo = nrFogo;
	}
	public String getDm_Vida_Pneu() {
		return dm_Vida_Pneu;
	}
	public String getDm_Vida_Pneu_Descricao() {
		if ("N".equals(dm_Vida_Pneu)) {
			return "Novo";
		}
		return dm_Vida_Pneu;
	}
	public void setDm_Vida_Pneu(String dmVidaPneu) {
		dm_Vida_Pneu = dmVidaPneu;
	}
	public double getNr_Mm_Sulco() {
		return nr_Mm_Sulco;
	}
	public void setNr_Mm_Sulco(double nrMmSulco) {
		nr_Mm_Sulco = nrMmSulco;
	}
	public long getNr_Pressao() {
		return nr_Pressao;
	}
	public void setNr_Pressao(long nrPressao) {
		nr_Pressao = nrPressao;
	}
	public long getOid_Pneu_Dimensao() {
		return oid_Pneu_Dimensao;
	}
	public void setOid_Pneu_Dimensao(long oidPneuDimensao) {
		oid_Pneu_Dimensao = oidPneuDimensao;
	}
	public long getOid_Fabricante_Pneu() {
		return oid_Fabricante_Pneu;
	}
	public void setOid_Fabricante_Pneu(long oidFabricantePneu) {
		oid_Fabricante_Pneu = oidFabricantePneu;
	}
	public long getOid_Modelo_Pneu() {
		return oid_Modelo_Pneu;
	}
	public void setOid_Modelo_Pneu(long oidModeloPneu) {
		oid_Modelo_Pneu = oidModeloPneu;
	}
	public long getOid_Fabricante_Banda() {
		return oid_Fabricante_Banda;
	}
	public void setOid_Fabricante_Banda(long oidFabricanteBanda) {
		oid_Fabricante_Banda = oidFabricanteBanda;
	}
	public long getOid_Banda() {
		return oid_Banda;
	}
	public void setOid_Banda(long oidBanda) {
		oid_Banda = oidBanda;
	}

	public String getArray() {
		return array;
	}
	public void setArray(String array) {
		this.array = array;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Stif_Pneu_InspecaoED [dm_Posicao=");
		builder.append(dm_Posicao);
		builder.append(", dm_Vida_Pneu=");
		builder.append(dm_Vida_Pneu);
		builder.append(", nr_Eixo=");
		builder.append(nr_Eixo);
		builder.append(", nr_Fogo=");
		builder.append(nr_Fogo);
		builder.append(", nr_Mm_Sulco=");
		builder.append(nr_Mm_Sulco);
		builder.append(", nr_Pressao=");
		builder.append(nr_Pressao);
		builder.append(", oid_Banda=");
		builder.append(oid_Banda);
		builder.append(", oid_Fabricante_Banda=");
		builder.append(oid_Fabricante_Banda);
		builder.append(", oid_Fabricante_Pneu=");
		builder.append(oid_Fabricante_Pneu);
		builder.append(", oid_Modelo_Pneu=");
		builder.append(oid_Modelo_Pneu);
		builder.append(", oid_Pneu_Dimensao=");
		builder.append(oid_Pneu_Dimensao);
		builder.append(", oid_Pneu_Inspecao=");
		builder.append(oid_Pneu_Inspecao);
		builder.append(", oid_Veiculo_Inspecao=");
		builder.append(oid_Veiculo_Inspecao);
		builder.append("]");
		return builder.toString();
	}
}
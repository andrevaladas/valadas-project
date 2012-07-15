package com.master.ed.relatorio;

import java.io.Serializable;

/**
 * @author Andre Valadas
 */
public class Veiculo implements Serializable {

	private static final long serialVersionUID = -7536102288136734073L;

	/** Tipo de Chassis */
	private long dm_Tipo_Chassis;
	private long nr_Pressao_Eixo_1;
	private long nr_Pressao_Eixo_2;
	private long nr_Pressao_Eixo_3;
	private long nr_Pressao_Eixo_4;

	private String nr_Frota;
	private long nr_Odometro;
	private String nr_Placa;
	private long dm_Tipo_Veiculo;
	private String nm_Modelo_Veiculo;
	private String tx_Observacao;
	private String tx_Observacao_Imagem;
	private long oid_Imagem;

	/** Pneus esquerdos */
	private Pneu l8ee;
	private Pneu l8ei;
	private Pneu l7ee;
	private Pneu l7ei;
	private Pneu l6ee;
	private Pneu l6ei;
	private Pneu l5ee;
	private Pneu l5ei;
	private Pneu l4ee;
	private Pneu l4ei;
	private Pneu l3ee;
	private Pneu l3ei;
	private Pneu l2ee;
	private Pneu l2ei;
	private Pneu l1ee;
	private Pneu l1ei;
	private Pneu tkee;
	private Pneu tkei;
	private Pneu tee;
	private Pneu tei;
	private Pneu d2e;
	private Pneu de;
	/** Pneus direitos */		
	private Pneu l8de;
	private Pneu l8di;
	private Pneu l7de;
	private Pneu l7di;
	private Pneu l6de;
	private Pneu l6di;
	private Pneu l5de;
	private Pneu l5di;
	private Pneu l4de;
	private Pneu l4di;
	private Pneu l3de;
	private Pneu l3di;
	private Pneu l2de;
	private Pneu l2di;
	private Pneu l1de;
	private Pneu l1di;
	private Pneu tkde;
	private Pneu tkdi;
	private Pneu tde;
	private Pneu tdi;
	private Pneu d2d;
	private Pneu dd;
	/** Pneus step */
	private Pneu stp1;
	private Pneu stp2;

	public long getDm_Tipo_Chassis() {
		return this.dm_Tipo_Chassis;
	}
	public void setDm_Tipo_Chassis(final long dmTipoChassis) {
		this.dm_Tipo_Chassis = dmTipoChassis;
	}
	public long getNr_Pressao_Eixo_1() {
		return this.nr_Pressao_Eixo_1;
	}
	public void setNr_Pressao_Eixo_1(final long nrPressaoEixo_1) {
		this.nr_Pressao_Eixo_1 = nrPressaoEixo_1;
	}
	public long getNr_Pressao_Eixo_2() {
		return this.nr_Pressao_Eixo_2;
	}
	public void setNr_Pressao_Eixo_2(final long nrPressaoEixo_2) {
		this.nr_Pressao_Eixo_2 = nrPressaoEixo_2;
	}
	public long getNr_Pressao_Eixo_3() {
		return this.nr_Pressao_Eixo_3;
	}
	public void setNr_Pressao_Eixo_3(final long nrPressaoEixo_3) {
		this.nr_Pressao_Eixo_3 = nrPressaoEixo_3;
	}
	public long getNr_Pressao_Eixo_4() {
		return this.nr_Pressao_Eixo_4;
	}
	public void setNr_Pressao_Eixo_4(final long nrPressaoEixo_4) {
		this.nr_Pressao_Eixo_4 = nrPressaoEixo_4;
	}
	public String getNr_Frota() {
		return this.nr_Frota;
	}
	public void setNr_Frota(final String nrFrota) {
		this.nr_Frota = nrFrota;
	}
	public long getNr_Odometro() {
		return this.nr_Odometro;
	}
	public void setNr_Odometro(final long nrOdometro) {
		this.nr_Odometro = nrOdometro;
	}
	public String getNr_Placa() {
		return this.nr_Placa;
	}
	public void setNr_Placa(final String nrPlaca) {
		this.nr_Placa = nrPlaca;
	}
	public long getDm_Tipo_Veiculo() {
		return this.dm_Tipo_Veiculo;
	}
	public String getNm_Tipo_Veiculo() {
		if (this.dm_Tipo_Veiculo == 1) {
			return "CAMINHÃO TOCO";
		} else if (this.dm_Tipo_Veiculo == 2) { 
			return "CAMINHÃO TRUCK";
		} else if (this.dm_Tipo_Veiculo == 3) {
			return "CAMINHONETE";
		} else if (this.dm_Tipo_Veiculo == 4) {
			return "CARRETA";
		} else if (this.dm_Tipo_Veiculo == 5) {
			return "CAVALO TRUCADO";
		} else if (this.dm_Tipo_Veiculo == 6) {
			return "CAVALO SIMPLES";
		} else if (this.dm_Tipo_Veiculo == 7) {
			return "ÔNIBUS";
		} else if (this.dm_Tipo_Veiculo == 8) {
			return "MICRO ÔNIBUS";
		} else if (this.dm_Tipo_Veiculo == 9) {
			return "PASSEIO";
		}
		return "";
	}
	public void setDm_Tipo_Veiculo(final long dmTipoVeiculo) {
		this.dm_Tipo_Veiculo = dmTipoVeiculo;
	}
	public String getNm_Modelo_Veiculo() {
		return this.nm_Modelo_Veiculo;
	}
	public void setNm_Modelo_Veiculo(final String nmModeloVeiculo) {
		this.nm_Modelo_Veiculo = nmModeloVeiculo;
	}
	public String getTx_Observacao() {
		return this.tx_Observacao;
	}
	public void setTx_Observacao(final String txObservacao) {
		this.tx_Observacao = txObservacao;
	}
	public String getTx_Observacao_Imagem() {
		return this.tx_Observacao_Imagem;
	}
	public void setTx_Observacao_Imagem(final String txObservacaoImagem) {
		this.tx_Observacao_Imagem = txObservacaoImagem;
	}
	public long getOid_Imagem() {
		return this.oid_Imagem;
	}
	public void setOid_Imagem(final long oidImagem) {
		this.oid_Imagem = oidImagem;
	}

	/** Pneus */
	public Pneu getL8ee() {
		return this.l8ee;
	}
	public void setL8ee(final Pneu l8ee) {
		this.l8ee = l8ee;
	}
	public Pneu getL8ei() {
		return this.l8ei;
	}
	public void setL8ei(final Pneu l8ei) {
		this.l8ei = l8ei;
	}
	public Pneu getL7ee() {
		return this.l7ee;
	}
	public void setL7ee(final Pneu l7ee) {
		this.l7ee = l7ee;
	}
	public Pneu getL7ei() {
		return this.l7ei;
	}
	public void setL7ei(final Pneu l7ei) {
		this.l7ei = l7ei;
	}
	public Pneu getL6ee() {
		return this.l6ee;
	}
	public void setL6ee(final Pneu l6ee) {
		this.l6ee = l6ee;
	}
	public Pneu getL6ei() {
		return this.l6ei;
	}
	public void setL6ei(final Pneu l6ei) {
		this.l6ei = l6ei;
	}
	public Pneu getL5ee() {
		return this.l5ee;
	}
	public void setL5ee(final Pneu l5ee) {
		this.l5ee = l5ee;
	}
	public Pneu getL5ei() {
		return this.l5ei;
	}
	public void setL5ei(final Pneu l5ei) {
		this.l5ei = l5ei;
	}
	public Pneu getL4ee() {
		return this.l4ee;
	}
	public void setL4ee(final Pneu l4ee) {
		this.l4ee = l4ee;
	}
	public Pneu getL4ei() {
		return this.l4ei;
	}
	public void setL4ei(final Pneu l4ei) {
		this.l4ei = l4ei;
	}
	public Pneu getL3ee() {
		return this.l3ee;
	}
	public void setL3ee(final Pneu l3ee) {
		this.l3ee = l3ee;
	}
	public Pneu getL3ei() {
		return this.l3ei;
	}
	public void setL3ei(final Pneu l3ei) {
		this.l3ei = l3ei;
	}
	public Pneu getL2ee() {
		return this.l2ee;
	}
	public void setL2ee(final Pneu l2ee) {
		this.l2ee = l2ee;
	}
	public Pneu getL2ei() {
		return this.l2ei;
	}
	public void setL2ei(final Pneu l2ei) {
		this.l2ei = l2ei;
	}
	public Pneu getL1ee() {
		return this.l1ee;
	}
	public void setL1ee(final Pneu l1ee) {
		this.l1ee = l1ee;
	}
	public Pneu getL1ei() {
		return this.l1ei;
	}
	public void setL1ei(final Pneu l1ei) {
		this.l1ei = l1ei;
	}
	public Pneu getTkee() {
		return this.tkee;
	}
	public void setTkee(final Pneu tkee) {
		this.tkee = tkee;
	}
	public Pneu getTkei() {
		return this.tkei;
	}
	public void setTkei(final Pneu tkei) {
		this.tkei = tkei;
	}
	public Pneu getTee() {
		return this.tee;
	}
	public void setTee(final Pneu tee) {
		this.tee = tee;
	}
	public Pneu getTei() {
		return this.tei;
	}
	public void setTei(final Pneu tei) {
		this.tei = tei;
	}
	public Pneu getD2e() {
		return this.d2e;
	}
	public void setD2e(final Pneu d2e) {
		this.d2e = d2e;
	}
	public Pneu getDe() {
		return this.de;
	}
	public void setDe(final Pneu de) {
		this.de = de;
	}
	public Pneu getL8de() {
		return this.l8de;
	}
	public void setL8de(final Pneu l8de) {
		this.l8de = l8de;
	}
	public Pneu getL8di() {
		return this.l8di;
	}
	public void setL8di(final Pneu l8di) {
		this.l8di = l8di;
	}
	public Pneu getL7de() {
		return this.l7de;
	}
	public void setL7de(final Pneu l7de) {
		this.l7de = l7de;
	}
	public Pneu getL7di() {
		return this.l7di;
	}
	public void setL7di(final Pneu l7di) {
		this.l7di = l7di;
	}
	public Pneu getL6de() {
		return this.l6de;
	}
	public void setL6de(final Pneu l6de) {
		this.l6de = l6de;
	}
	public Pneu getL6di() {
		return this.l6di;
	}
	public void setL6di(final Pneu l6di) {
		this.l6di = l6di;
	}
	public Pneu getL5de() {
		return this.l5de;
	}
	public void setL5de(final Pneu l5de) {
		this.l5de = l5de;
	}
	public Pneu getL5di() {
		return this.l5di;
	}
	public void setL5di(final Pneu l5di) {
		this.l5di = l5di;
	}
	public Pneu getL4de() {
		return this.l4de;
	}
	public void setL4de(final Pneu l4de) {
		this.l4de = l4de;
	}
	public Pneu getL4di() {
		return this.l4di;
	}
	public void setL4di(final Pneu l4di) {
		this.l4di = l4di;
	}
	public Pneu getL3de() {
		return this.l3de;
	}
	public void setL3de(final Pneu l3de) {
		this.l3de = l3de;
	}
	public Pneu getL3di() {
		return this.l3di;
	}
	public void setL3di(final Pneu l3di) {
		this.l3di = l3di;
	}
	public Pneu getL2de() {
		return this.l2de;
	}
	public void setL2de(final Pneu l2de) {
		this.l2de = l2de;
	}
	public Pneu getL2di() {
		return this.l2di;
	}
	public void setL2di(final Pneu l2di) {
		this.l2di = l2di;
	}
	public Pneu getL1de() {
		return this.l1de;
	}
	public void setL1de(final Pneu l1de) {
		this.l1de = l1de;
	}
	public Pneu getL1di() {
		return this.l1di;
	}
	public void setL1di(final Pneu l1di) {
		this.l1di = l1di;
	}
	public Pneu getTkde() {
		return this.tkde;
	}
	public void setTkde(final Pneu tkde) {
		this.tkde = tkde;
	}
	public Pneu getTkdi() {
		return this.tkdi;
	}
	public void setTkdi(final Pneu tkdi) {
		this.tkdi = tkdi;
	}
	public Pneu getTde() {
		return this.tde;
	}
	public void setTde(final Pneu tde) {
		this.tde = tde;
	}
	public Pneu getTdi() {
		return this.tdi;
	}
	public void setTdi(final Pneu tdi) {
		this.tdi = tdi;
	}
	public Pneu getD2d() {
		return this.d2d;
	}
	public void setD2d(final Pneu d2d) {
		this.d2d = d2d;
	}
	public Pneu getDd() {
		return this.dd;
	}
	public void setDd(final Pneu dd) {
		this.dd = dd;
	}
	public Pneu getStp1() {
		return this.stp1;
	}
	public void setStp1(final Pneu stp1) {
		this.stp1 = stp1;
	}
	public Pneu getStp2() {
		return this.stp2;
	}
	public void setStp2(final Pneu stp2) {
		this.stp2 = stp2;
	}
}
package DemoClassify;

public class myResult {

	private String calcRes;
	private String oriRes;
	private double res;
	public double getRes() {
		return res;
	}
	public void setRes(double res) {
		this.res = res;
	}
	public myResult(String calcRes, String oriRes) {
		super();
		this.calcRes = calcRes;
		this.oriRes = oriRes;
	}
	public myResult(String oriRes) {
		super();
		this.calcRes = "";
		this.oriRes = oriRes;
	}
	public myResult() {
		super();
		this.calcRes = "";
		this.oriRes = "";
	}
	public String getCalcRes() {
		return calcRes;
	}
	public void setCalcRes(String calcRes) {
		this.calcRes = calcRes;
	}
	public String getOriRes() {
		return oriRes;
	}
	public void setOriRes(String oriRes) {
		this.oriRes = oriRes;
	}

}

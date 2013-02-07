package DemoClassify;

public class MyResult {

	private String calcRes;
	private String oriRes;
	private double res;
	
	public double getRes() {
		return this.res;
	}
	public void setRes(double res) {
		this.res = res;
	}
	public MyResult(String calcRes, String oriRes) {
		super();
		this.calcRes = calcRes;
		this.oriRes = oriRes;
	}
	public MyResult(String oriRes) {
		super();
		this.calcRes = "";
		this.oriRes = oriRes;
	}
	public MyResult() {
		super();
		this.calcRes = "";
		this.oriRes = "";
	}
	public String getCalcRes() {
		return this.calcRes;
	}
	public void setCalcRes(String calcRes) {
		this.calcRes = calcRes;
	}
	public String getOriRes() {
		return this.oriRes;
	}
	public void setOriRes(String oriRes) {
		this.oriRes = oriRes;
	}

}

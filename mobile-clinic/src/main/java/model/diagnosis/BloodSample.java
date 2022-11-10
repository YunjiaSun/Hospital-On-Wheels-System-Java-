package model.diagnosis;

public class BloodSample {
    private double whiteBloodCell;
    private double thyroxine;
    private double triiodothyronine;

    public BloodSample(double whiteBloodCell, double thyroxine, double triiodothyronine) {
        this.setWhiteBloodCell(whiteBloodCell);
        this.setThyroxine(thyroxine);
        this.setTriiodothyronine(triiodothyronine);
    }

    public double getWhiteBloodCell() {
        return whiteBloodCell;
    }

    public void setWhiteBloodCell(double whiteBloodCell) {
        this.whiteBloodCell = whiteBloodCell;
    }

    public double getTriiodothyronine() {
        return triiodothyronine;
    }

    public void setTriiodothyronine(double triiodothyronine) {
        this.triiodothyronine = triiodothyronine;
    }

    public double getThyroxine() {
        return thyroxine;
    }

    public void setThyroxine(double thyroxine) {
        this.thyroxine = thyroxine;
    }
}

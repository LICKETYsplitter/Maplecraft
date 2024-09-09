package net.licketysplitter.maplecraft.block.entity.custom;

public class EvaporatorPan{
    private static final int MAX_FULLNESS = 1000;
    private static final float PROGRESS_FACTOR = 0.001f;
    public int fullness = 0;
    public float progress = 0.0f;

    public EvaporatorPan(){}

    public Boolean isEmpty(){ return fullness == 0; }

    public Boolean isFull(){ return fullness == MAX_FULLNESS; }

    public void empty(){
        fullness = 0;
        progress = 0.0f;
    }

    public void addFluid(int mb){
        if(mb + fullness <= MAX_FULLNESS) {
            fullness += mb;
            progress = progress * ((float) ((MAX_FULLNESS - mb) / MAX_FULLNESS));
        }
    }

    public void evaporate(){
        if(!isEmpty() && progress < 1.0f){
            progress+= PROGRESS_FACTOR * ((float)(MAX_FULLNESS/fullness));
        }
    }

    public boolean isReady(){ return isFull() && progress >= 1.0f;}
}

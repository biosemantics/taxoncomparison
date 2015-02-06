package edu.arizona.biosemantics.taxoncomparison.model;



import edu.arizona.biosemantics.matrixreview.shared.model.core.Organ;
import edu.arizona.biosemantics.matrixreview.shared.model.core.Value;


public class OrganCharacterState{

	private WeightedCharacter character= null;
	private Value state = null;
	private Organ organ = null;
	
	public Organ getOrgan() {
		return organ;
	}

	public void setOrgan(Organ organ) {
		this.organ = organ;
	}

	
	/*float weight = 0;
	
	public float getWeight() {
		return weight;
	}

	public void setWeight(float weight) {
		this.weight = weight;
	}*/

	public OrganCharacterState(Organ organ, WeightedCharacter character, Value state) {
		this.organ = organ;
		this.character = character;
		this.state = state;
	}

	public WeightedCharacter getCharacter() {
		return character;
	}

	public void setCharacter(WeightedCharacter character) {
		this.character = character;
	}

	public Value getState() {
		return state;
	}

	public void setState(Value state) {
		this.state = state;
	}

	@Override
	public boolean equals(Object o){
		if(o == null) return false;
		if(o instanceof OrganCharacterState){
			OrganCharacterState comp = (OrganCharacterState) o;
			String compareString = comp.getOrgan().toString()+comp.getCharacter().toString()+comp.getState().toString();
			String thisString = this.organ.toString()+this.character.toString()+this.state.toString();
			if(compareString.compareTo(thisString)==0) return true;
			else return false;
		}else{
			return false;
		}
	}
	
	@Override
	public String toString(){
		return this.organ.toString()+": "+this.character.toString()+": "+this.state.toString();
	}

	/*@Override
	public int compareTo(Object o) {
		if(this.equals(o)) return 0;
		else if(o instanceof OrganCharacterState){
			if(this.organ.getName().compareTo(((OrganCharacterState)o).getOrgan().getName())!=0){
				return this.organ.getName().compareTo(((OrganCharacterState)o).getOrgan().getName());
			}else{
				
			}
		}

	}*/
}

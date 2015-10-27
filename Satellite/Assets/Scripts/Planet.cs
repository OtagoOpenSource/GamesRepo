using UnityEngine;
using System.Collections;

public class Planet : MonoBehaviour {

	public Color color;

	// Use this for initialization
	void Start () {
		gameObject.GetComponent<Renderer>().material.color = this.color;
	}
	
	// Update is called once per frame
	void Update () {
	
	}
}

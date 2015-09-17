using UnityEngine;
using System.Collections;

public class PlanetoidGravity : MonoBehaviour {

	// Use this for initialization
	void Start () {
	
	}
	
	// Update is called once per frame
	void Update () {
		//Rigidbody rb = GetComponent<Rigidbody>();
		//Debug.Log (Physics.gravity.ToString ("F4"));
		Vector3 newGravity = -transform.position;
		newGravity = newGravity.normalized * 9.81f;
		//diff.x = diff.x * 9.81f;
		//diff.y = diff.y * 9.81f;
		//diff.z = diff.z * 9.81f;
		//Debug.Log (diff.ToString ("F4"));
		Physics.gravity = newGravity;
	}
}

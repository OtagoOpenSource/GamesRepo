using UnityEngine;
using System.Collections;

public class PlanetoidGravity : MonoBehaviour {

	Vector3 lastPosition;

	// Use this for initialization
	void Start () {
		lastPosition = transform.localPosition;
	}
	
	// Update is called once per frame
	void Update () {
		//Rigidbody rb = GetComponent<Rigidbody>();
		//Debug.Log (Physics.gravity.ToString ("F4"));
		Vector3 newGravity = -transform.localPosition.normalized;

		transform.rotation = Quaternion.FromToRotation(Vector3.down, newGravity);


		//diff.x = diff.x * 9.81f;
		//diff.y = diff.y * 9.81f;
		//diff.z = diff.z * 9.81f;
		//Debug.Log (newGravity.ToString("F4"));
		Physics.gravity = newGravity*9.81f;


		lastPosition = transform.localPosition;

	}
}

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
		Vector3 newGravity = -transform.localPosition;
		newGravity = newGravity.normalized * 9.81f;
		//diff.x = diff.x * 9.81f;
		//diff.y = diff.y * 9.81f;
		//diff.z = diff.z * 9.81f;
		//Debug.Log (newGravity.ToString("F4"));
		Physics.gravity = newGravity;

		transform.rotation = Quaternion.FromToRotation(lastPosition, transform.localPosition);

		lastPosition = transform.localPosition;

	}
}

using UnityEngine;
using System.Collections;


[RequireComponent(typeof(Rigidbody))]

public class Gravity : MonoBehaviour {

	const float G = 0.01f;

	Rigidbody m_Rigidbody;

	Rigidbody p_Rigidbody;

	public Vector3 initialVelocity = new Vector3(0f,0f,0f);
	Vector3 velocity;
	Vector3 acceleration;

	// Use this for initialization
	void Start () {
		m_Rigidbody = GetComponent<Rigidbody>();

		p_Rigidbody = transform.parent.GetComponent<Rigidbody> ();
		velocity = transform.TransformVector (initialVelocity);
	}
	
	// Update is called once per frame
	void FixedUpdate () {
		Vector3 r = transform.parent.position-transform.position;
			
		acceleration = r.normalized * (G * p_Rigidbody.mass / r.sqrMagnitude);

		velocity += acceleration * Time.deltaTime;
		transform.position = transform.position+velocity*Time.deltaTime;
	}


}

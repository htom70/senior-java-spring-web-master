package hu.ponte.hr.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Getter
public class Image {
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id", nullable = false)
	private Long id;

	private String name;

	private String mimeType;

	@Lob
	private String digitalSign;

	@Lob
	private byte[] content;

	public Image(String name, String mimeType, String digitalSign, byte[] content) {
		this.name = name;
		this.mimeType = mimeType;
		this.digitalSign = digitalSign;
		this.content = content;
	}
}

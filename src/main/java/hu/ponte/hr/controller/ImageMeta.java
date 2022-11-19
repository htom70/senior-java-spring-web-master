package hu.ponte.hr.controller;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

/**
 * @author zoltan
 */
@Getter
@Builder
@AllArgsConstructor
public class ImageMeta {
	private String id;
	private String name;
	private String mimeType;
	private long size;
	private String digitalSign;
}

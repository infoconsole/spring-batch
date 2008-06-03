package org.springframework.batch.item;

import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.mapping.FieldSet;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.sample.Foo;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;

public class MultiResourceItemReaderFlatFileTests extends CommonItemStreamItemReaderTests {

	protected ItemReader getItemReader() throws Exception {

		MultiResourceItemReader multiReader = new MultiResourceItemReader();
		FlatFileItemReader fileReader = new FlatFileItemReader();

		fileReader.setFieldSetMapper(new FieldSetMapper() {
			public Object mapLine(FieldSet fs) {
				Foo foo = new Foo();
				foo.setValue(fs.readInt(0));
				return foo;
			}
		});
		fileReader.setSaveState(true);

		multiReader.setDelegate(fileReader);

		Resource r1 = new ByteArrayResource("1\n2\n".getBytes());
		Resource r2 = new ByteArrayResource("".getBytes());
		Resource r3 = new ByteArrayResource("3\n".getBytes());
		Resource r4 = new ByteArrayResource("4\n5\n".getBytes());

		multiReader.setResources(new Resource[] { r1, r2, r3, r4 });
		multiReader.setSaveState(true);
		multiReader.afterPropertiesSet();

		return multiReader;
	}

}

package be.butskri.playground.batch;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.jdbc.core.JdbcTemplate;

import be.butskri.playground.batch.VreemdelingIdReader;

@RunWith(MockitoJUnitRunner.class)
public class VreemdelingIdReaderTest {

	private Long ID1 = 1L;
	private Long ID2 = 2L;
	private Long ID3 = 3L;
	private Long ID4 = 4L;
	private Long ID5 = 5L;
	private Long ID6 = 6L;
	private Long ID7 = 7L;
	private Long ID8 = 8L;

	@Mock
	private JdbcTemplate jdbcTemplateMock;
	private VreemdelingIdReader vreemdelingIdReader;

	@Before
	public void setUp() {
		vreemdelingIdReader = new VreemdelingIdReader(jdbcTemplateMock);
		vreemdelingIdReader.setMaxPageSize(3);
	}

	@Test
	public void readMet0VreemdelingIds() throws Exception {
		setUpQuery(0L);

		assertReadIds((Long) null);
	}

	@Test
	public void readMet1VreemdelingIds() throws Exception {
		setUpQuery(0L, ID1);

		assertReadIds(ID1, null);
	}

	@Test
	public void readMet2VreemdelingIds() throws Exception {
		setUpQuery(0L, ID1, ID2);

		assertReadIds(ID1, ID2, null);
	}

	@Test
	public void readMet3VreemdelingIds() throws Exception {
		setUpQuery(0L, ID1, ID2, ID3);

		assertReadIds(ID1, ID2, ID3, null);
	}

	@Test
	public void readMet4VreemdelingIds() throws Exception {
		setUpQuery(0L, ID1, ID2, ID3, ID4);
		setUpQuery(ID3, ID4);

		assertReadIds(ID1, ID2, ID3, ID4, null);
	}

	@Test
	public void readMet5VreemdelingIds() throws Exception {
		setUpQuery(0L, ID1, ID2, ID3, ID4);
		setUpQuery(ID3, ID4, ID5);

		assertReadIds(ID1, ID2, ID3, ID4, ID5, null);
	}

	@Test
	public void readMet6VreemdelingIds() throws Exception {
		setUpQuery(0L, ID1, ID2, ID3, ID4);
		setUpQuery(ID3, ID4, ID5, ID6);

		assertReadIds(ID1, ID2, ID3, ID4, ID5, ID6, null);
	}

	@Test
	public void readMet7VreemdelingIds() throws Exception {
		setUpQuery(0L, ID1, ID2, ID3, ID4);
		setUpQuery(ID3, ID4, ID5, ID6, ID7);
		setUpQuery(ID6, ID7);

		assertReadIds(ID1, ID2, ID3, ID4, ID5, ID6, ID7, null);
	}

	@Test
	public void readMet8VreemdelingIds() throws Exception {
		setUpQuery(0L, ID1, ID2, ID3, ID4);
		setUpQuery(ID3, ID4, ID5, ID6, ID7);
		setUpQuery(ID6, ID7, ID8);

		assertReadIds(ID1, ID2, ID3, ID4, ID5, ID6, ID7, ID8, null);
	}

	private void assertReadIds(Long... expectedIds) throws Exception {
		for (Long expectedId : expectedIds) {
			assertEquals(expectedId, vreemdelingIdReader.read());
		}
	}

	private void setUpQuery(long arg, Long... returnValues) {
		when(jdbcTemplateMock.queryForList(VreemdelingIdReader.QUERY, Long.class, arg)) //
				.thenReturn(Arrays.asList(returnValues));

	}
}

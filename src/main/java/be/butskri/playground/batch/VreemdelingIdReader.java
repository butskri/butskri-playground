package be.butskri.playground.batch;

import java.util.List;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.jdbc.core.JdbcTemplate;

public class VreemdelingIdReader implements ItemReader<Long> {

	static final String QUERY = "select id from hos_vreemdeling where id > ? and (batch_verwerkt is null or batch_verwerkt = 0) order by id";
	private JdbcTemplate jdbcTemplate;
	private int maxPageSize = 1000;
	private Long lastFetchedId;
	private List<Long> fetchedIds;
	private int index;

	public VreemdelingIdReader(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
		reset();
	}

	public void setMaxPageSize(int maxPageSize) {
		this.maxPageSize = maxPageSize;
		this.jdbcTemplate.setMaxRows(maxPageSize + 1);
	}

	@Override
	public Long read() throws Exception, UnexpectedInputException, ParseException {
		if (nextIdsToBeFetched()) {
			fetchNextIds();
		}
		return nextId();
	}

	private boolean nextIdsToBeFetched() {
		if (fetchedIds == null) {
			return true;
		}
		return endOfIdListReached() && moreIdsLeft();
	}

	private boolean moreIdsLeft() {
		return fetchedIds.size() > maxPageSize;
	}

	private boolean endOfIdListReached() {
		return index == fetchedIds.size() || index == maxPageSize;
	}

	private void fetchNextIds() {
		fetchedIds = jdbcTemplate.queryForList(QUERY, Long.class, lastFetchedId);
		index = 0;
	}

	private Long nextId() {
		if (index >= fetchedIds.size()) {
			return null;
		}
		lastFetchedId = fetchedIds.get(index);
		index++;
		return lastFetchedId;
	}

	private void reset() {
		this.lastFetchedId = 0L;
		this.fetchedIds = null;
		this.index = 0;
	}
}

import java.util.*

// server-side implementation of Google Finance service to add, update and fetch prices for a given stock

data class FetchResult(
    val timeStamp: String,
    val curPrice: Int,
    val lowPrice: Int,
    val highPrice: Int
)

data class GoogleFinanceData(
    val timeStamp: String,
    val price: Int // a price in a given point of time
) : Comparable<GoogleFinanceData> {
    override fun compareTo(other: GoogleFinanceData): Int {
        return price.compareTo(other.price)
    }

    override fun equals(other: Any?): Boolean {
        val oth = other as? GoogleFinanceData ?: return false
        return timeStamp == oth.timeStamp
    }

    override fun hashCode(): Int {
        return Objects.hash(timeStamp)
    }
}

class GoogleFinanceServer {
    private val lowPriorityQueue = PriorityQueue<GoogleFinanceData>()
    private val highPriorityQueue = PriorityQueue<GoogleFinanceData>(11, Comparator.reverseOrder())
    private var latestPrice = 0
    private var latestTimestamp = ""

    // fetches latest price, low and high values .. works in O(1) time
    fun fetchData(): FetchResult {
        // O(1) runtime for peek:
        val lowPrice = if (lowPriorityQueue.isEmpty()) 0 else lowPriorityQueue.peek().price
        val highPrice =
            if (highPriorityQueue.isEmpty()) lowPrice else highPriorityQueue.peek().price

        return FetchResult(latestTimestamp, latestPrice, lowPrice, highPrice)
    }

    // O(log N) runtime for PQ insertion
    // Uses O(2N) space, which is still O(N) .. alternative: Use a TreeSet for half space, but less efficient lookup
    fun add(timeStamp: String, price: Int) {
        val data = GoogleFinanceData(timeStamp, price)

        lowPriorityQueue.add(data)
        highPriorityQueue.add(data)

        latestTimestamp = timeStamp
        latestPrice = price
    }

    // updates are expensive, O(N), but do not happen as often as adds.
    // Could use a TreeSet for O(log N) remove, but then fetchData would also take O(log N)
    fun update(timeStamp: String, price: Int) {
        val searched = GoogleFinanceData(timeStamp, price)

        // equals only based on timestamp, but price changed
        if (lowPriorityQueue.remove(searched)) {
            lowPriorityQueue.add(searched)
        }

        if (highPriorityQueue.remove(searched)) {
            highPriorityQueue.add(searched)
        }

        if (timeStamp == latestTimestamp) {
            latestPrice = price
        }
    }
}

val server = GoogleFinanceServer()

server.add("1", 100)
server.add("2", 20)
server.add("3", 50)

server.fetchData() // returns [curTS: 3, cur: 50, low: 20, high: 100]

server.update("1", 10) // new high and new low!

server.fetchData() // return [curTS: 3, cur: 50, low: 10, high: 50]

server.add("xx", 0)

server.fetchData() // return [curTS: xx, cur: 0, low: 0, high: 50]

package ru.shabarov.collections

//Partition
// Return customers who have more undelivered orders than delivered
fun Shop.getCustomersWithMoreUndeliveredOrdersThanDelivered(): Set<Customer> = customers.filter { it ->
    val (delivered, notDelivered) = it.orders.partition {it.isDelivered}
    notDelivered.size > delivered.size
}.toSet()

//Fold
// Return the set of products that were ordered by every customer
fun Shop.getSetOfProductsOrderedByEveryCustomer(): Set<Product> {
    val allProducts = customers.flatMap { it -> it.orders.flatMap { it.products }}.toSet()
    return customers.fold(allProducts, {
            orderedByAll, customer -> orderedByAll.intersect(customer.orders.flatMap { it.products }.toSet())
    })
}

//Compound tasks
// Return the most expensive product among all delivered products
// (use the Order.isDelivered flag)
fun Customer.getMostExpensiveDeliveredProduct(): Product? {
    return orders.filter {it.isDelivered}.flatMap {it.products}.maxBy {it.price}
}

// Return how many times the given product was ordered.
// Note: a customer may order the same product for several times.
fun Shop.getNumberOfTimesProductWasOrdered(product: Product): Int {
    return customers.flatMap {it.orders.flatMap { it.products }}.filter { it == product }.count()
}

//Get used to new style
fun doSomethingStrangeWithCollection(collection: Collection<String>): Collection<String>? {

    val groupsByLength = collection. groupBy { s -> s.length }

    val maximumSizeOfGroup = groupsByLength.values.map { group -> group.size }.max()

    return groupsByLength.values.firstOrNull { group -> group.size == maximumSizeOfGroup }
}

// Java version:
//public Collection<String> doSomethingStrangeWithCollection(
//Collection<String> collection
//) {
//    Map<Integer, List<String>> groupsByLength = Maps.newHashMap();
//    for (String s : collection) {
//        List<String> strings = groupsByLength.get(s.length());
//        if (strings == null) {
//            strings = Lists.newArrayList();
//            groupsByLength.put(s.length(), strings);
//        }
//        strings.add(s);
//    }
//
//    int maximumSizeOfGroup = 0;
//    for (List<String> group : groupsByLength.values()) {
//        if (group.size() > maximumSizeOfGroup) {
//            maximumSizeOfGroup = group.size();
//        }
//    }
//
//    for (List<String> group : groupsByLength.values()) {
//        if (group.size() == maximumSizeOfGroup) {
//            return group;
//        }
//    }
//    return null;
//}
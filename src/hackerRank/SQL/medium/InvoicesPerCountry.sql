SELECT 
    co.country_name, 
    count(*), 
    AVG(inv.total_price)
FROM
    country co
Join city ci on co.id = ci.country_id
Join customer cu on ci.id = cu.city_id
Join invoice inv on cu.id = inv.customer_id
group BY 
    co.country_name
HAVING
    AVG(inv.total_price) > (select AVG(total_price) from invoice)
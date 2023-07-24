SELECT
    ci.city_name,
    pr.product_name,
    ROUND(sum(ii.line_total_price), 2) as tot
FROM
    city as ci
JOIN customer cu on ci.id = cu.city_id
JOIN invoice inv on cu.id = inv.customer_id
JOIN invoice_item ii on inv.id = ii.invoice_id
JOIN product pr on ii.product_id = pr.id
group by
    ci.city_name, pr.product_name
order BY
    tot desc, ci.city_name, pr.product_name
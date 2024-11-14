-- 현재 인덱스 상태 확인
SHOW INDEX FROM order_item;

-- 인덱스 삭제 (에러가 발생해도 계속 진행)
DROP INDEX idx_order_item_date_status ON order_item;

-- 프로파일링 시작
SET profiling = 1;

-- 인덱스 생성 전 실행 계획 확인
EXPLAIN
SELECT p.*
FROM product p
JOIN order_item oi ON p.id = oi.product_id
WHERE oi.created_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 DAY) AND NOW()
AND oi.order_status = 'COMPLETED'
GROUP BY oi.product_id
ORDER BY SUM(oi.quantity) DESC
LIMIT 5;

-- 실제 쿼리 실행 (인덱스 생성 전)
SELECT p.*
FROM product p
JOIN order_item oi ON p.id = oi.product_id
WHERE oi.created_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 DAY) AND NOW()
AND oi.order_status = 'COMPLETED'
GROUP BY oi.product_id
ORDER BY SUM(oi.quantity) DESC
LIMIT 5;

-- 인덱스 생성
CREATE INDEX idx_order_item_date_status
ON order_item (created_at, order_status);

-- 인덱스가 실제로 생성되었는지 확인
SHOW INDEX FROM order_item;

-- 옵티마이저가 인덱스를 사용하는지 확인
EXPLAIN
SELECT p.*
FROM product p
JOIN order_item oi ON p.id = oi.product_id
WHERE oi.created_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 DAY) AND NOW()
AND oi.order_status = 'COMPLETED'
GROUP BY oi.product_id
ORDER BY SUM(oi.quantity) DESC
LIMIT 5;

-- 인덱스 생성 후 실제 쿼리 실행
SELECT p.*
FROM product p
JOIN order_item oi ON p.id = oi.product_id
WHERE oi.created_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 DAY) AND NOW()
AND oi.order_status = 'COMPLETED'
GROUP BY oi.product_id
ORDER BY SUM(oi.quantity) DESC
LIMIT 5;

-- 실행 시간 확인
SHOW PROFILES;

-- 테이블과 인덱스 상태 확인
SHOW TABLE STATUS LIKE 'order_item';

-- 인덱스 사용 통계 (기본 정보)
SHOW INDEX FROM order_item;

-- 전체 데이터 중 필터링되는 비율 확인 (3일 기준)
SELECT
    COUNT(*) as total_records,
    SUM(CASE
        WHEN created_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 DAY) AND NOW()
        AND order_status = 'COMPLETED' THEN 1
        ELSE 0
    END) as filtered_records,
    (SUM(CASE
        WHEN created_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 DAY) AND NOW()
        AND order_status = 'COMPLETED' THEN 1
        ELSE 0
    END) / COUNT(*)) * 100 as filter_percentage
FROM order_item;

-- 3일간의 주문 건수 확인
SELECT COUNT(*) as orders_in_3_days
FROM order_item
WHERE created_at BETWEEN DATE_SUB(NOW(), INTERVAL 3 DAY) AND NOW()
AND order_status = 'COMPLETED';

-- 프로파일링 종료
SET profiling = 0;
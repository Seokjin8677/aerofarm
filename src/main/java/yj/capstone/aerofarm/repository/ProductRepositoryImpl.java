package yj.capstone.aerofarm.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import yj.capstone.aerofarm.domain.product.Product;
import yj.capstone.aerofarm.domain.product.ProductCategory;
import yj.capstone.aerofarm.dto.ProductStoreInfoDto;
import yj.capstone.aerofarm.dto.QProductStoreInfoDto;
import yj.capstone.aerofarm.dto.QStoreReviewDto;
import yj.capstone.aerofarm.dto.StoreReviewDto;
import yj.capstone.aerofarm.dto.response.ProductAdminListResponseDto;
import yj.capstone.aerofarm.dto.response.QProductAdminListResponseDto;
import yj.capstone.aerofarm.repository.support.Querydsl5RepositorySupport;

import static yj.capstone.aerofarm.domain.member.QMember.member;
import static yj.capstone.aerofarm.domain.product.QProduct.product;
import static yj.capstone.aerofarm.domain.product.QProductReview.productReview;

public class ProductRepositoryImpl extends Querydsl5RepositorySupport implements ProductRepositoryCustom {

    public ProductRepositoryImpl() {
        super(Product.class);
    }

    @Override
    public Page<ProductStoreInfoDto> findProductInfo(ProductCategory category, String order, Pageable pageable) {
        return applyPagination(pageable,
                query -> query
                        .select(new QProductStoreInfoDto(
                                product.id.as("productId"),
                                product.imageUrl,
                                product.name,
                                product.price.money.as("price"),
                                productReview.review.count().as("reviewCnt"),
                                productReview.score.avg().as("scoreAvg")))
                        .from(product)
                        .leftJoin(product.productReviews, productReview)
                        .groupBy(product.id, product.imageUrl, product.name, product.price)
                        .where(categoryEq(category)),

                query -> query
                        .select(product.count())
                        .from(product)
                        .where(categoryEq(category)));
    }

    @Override
    public Page<StoreReviewDto> findStoreReview(Pageable pageable, Long productId) {
        return applyPagination(pageable,
                query -> query
                        .select(new QStoreReviewDto(
                                member.nickname.as("reviewer"),
                                productReview.review,
                                productReview.score,
                                productReview.createdDate))
                        .from(productReview)
                        .innerJoin(productReview.member, member)
                        .where(productReview.product.id.eq(productId)),
                query -> query
                        .select(productReview.count())
                        .from(productReview)
                        .where(productReview.product.id.eq(productId))
        );
    }

    @Override
    public Page<ProductAdminListResponseDto> findProductAdminList(Pageable pageable) {
        return applyPagination(pageable,
                query -> query
                        .select(new QProductAdminListResponseDto(
                                product.id.as("productId"),
                                product.name,
                                product.price.money.as("price"),
                                product.saleCount.stock.as("saleCount"),
                                product.stock.stock.as("stock"),
                                productReview.review.count().as("reviewCnt"),
                                productReview.score.avg().as("scoreAvg")))
                        .from(product)
                        .leftJoin(product.productReviews, productReview)
                        .groupBy(product.id, product.name, product.price),

                query -> query
                        .select(product.count())
                        .from(product));
    }

    private BooleanExpression categoryEq(ProductCategory category) {
        return category == null ? null : product.category.eq(category);
    }
}

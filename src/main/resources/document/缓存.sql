@Cacheable 作用： 把方法的返回值添加到Ehache中作缓存
value属性： 制定ehcahce配置文件中的缓存策略，如果没有给定value，name则表示使用默认的缓存策略。

Key 属性：给存储的值起个名称。在查询时如果有名称相同的，那么则知己从缓存中将数据返回

@Cacheable(value="users",key="#pageable.pageSize")
public Page<Users> findUserByPage(Pageable pageable) {
return this.usersRepository.findAll(pageable);
}

---------------------------------------------------------

@CacheEvict 作用：清除缓存




/*
 * Copyright 2011 JBoss Inc
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.drools.guvnor.server;

import org.drools.guvnor.client.rpc.CategoryPageRequest;
import org.drools.guvnor.client.rpc.CategoryPageRow;
import org.drools.guvnor.client.rpc.CategoryService;
import org.drools.guvnor.client.rpc.PageResponse;
import org.drools.guvnor.client.rpc.TableDataResult;
import org.drools.guvnor.server.security.CategoryPathType;
import org.drools.repository.RulesRepository;
import org.jboss.seam.annotations.AutoCreate;
import org.jboss.seam.annotations.Create;
import org.jboss.seam.annotations.In;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.remoting.WebRemote;
import org.jboss.seam.annotations.security.Restrict;

import com.google.gwt.user.client.rpc.SerializationException;

@Name("org.drools.guvnor.client.rpc.CategoryService")
@AutoCreate
public class RepositoryCategoryService
    implements
    CategoryService {
    @In
    private RulesRepository              repository;

    private static final long            serialVersionUID             = 12365;

    private final ServiceSecurity              serviceSecurity              = new ServiceSecurity();
    private final RepositoryCategoryOperations repositoryCategoryOperations = new RepositoryCategoryOperations();

    @Create
    public void create() {
        repositoryCategoryOperations.setRulesRepository( getRulesRepository() );
    }

    /* This is called in hosted mode when creating "by hand" */
    public void setRulesRepository(RulesRepository repository) {
        this.repository = repository;
        create();
    }

    public RulesRepository getRulesRepository() {
        return repository;
    }

    @WebRemote
    @Restrict("#{identity.loggedIn}")
    public String[] loadChildCategories(String categoryPath) {
        return repositoryCategoryOperations.loadChildCategories( categoryPath );
    }

    @WebRemote
    public Boolean createCategory(String path,
                                  String name,
                                  String description) {
        serviceSecurity.checkSecurityIsAdmin();
        return repositoryCategoryOperations.createCategory( path,
                                                            name,
                                                            description );
    }

    @WebRemote
    @Restrict("#{identity.loggedIn}")
    public void renameCategory(String fullPathAndName,
                               String newName) {
        repositoryCategoryOperations.renameCategory( fullPathAndName,
                                                     newName );
    }

    /**
     * loadRuleListForCategories
     *
     * Role-based Authorization check: This method only returns rules that the user has
     * permission to access. The user is considered to has permission to access the particular category when:
     * The user has ANALYST_READ role or higher (i.e., ANALYST) to this category
     * 
     * @deprecated in favour of {@link loadRuleListForCategories(CategoryPageRequest)}
     */
    @WebRemote
    @Restrict("#{identity.loggedIn}")
    public TableDataResult loadRuleListForCategories(String categoryPath,
                                                     int skip,
                                                     int numRows,
                                                     String tableConfig) throws SerializationException {
        return repositoryCategoryOperations.loadRuleListForCategories( categoryPath,
                                                                       skip,
                                                                       numRows,
                                                                       tableConfig );
    }

    @WebRemote
    @Restrict("#{identity.loggedIn}")
    public PageResponse<CategoryPageRow> loadRuleListForCategories(CategoryPageRequest request) throws SerializationException {
        if ( request == null ) {
            throw new IllegalArgumentException( "request cannot be null" );
        }
        if ( request.getPageSize() != null && request.getPageSize() < 0 ) {
            throw new IllegalArgumentException( "pageSize cannot be less than zero." );
        }

        // Role-based Authorization check: This method only returns rules that
        // the user has permission to access. The user is considered to has
        // permission to access the particular category when: The user has
        // ANALYST_READ role or higher (i.e., ANALYST) to this category
        if ( !serviceSecurity.isSecurityIsAnalystReadWithTargetObject( new CategoryPathType( request.getCategoryPath() ) ) ) {
            return new PageResponse<CategoryPageRow>();
        }

        return repositoryCategoryOperations.loadRuleListForCategories( request );
    }

    @WebRemote
    @Restrict("#{identity.loggedIn}")
    public void removeCategory(String categoryPath) throws SerializationException {
        repositoryCategoryOperations.removeCategory( categoryPath );
    }

}

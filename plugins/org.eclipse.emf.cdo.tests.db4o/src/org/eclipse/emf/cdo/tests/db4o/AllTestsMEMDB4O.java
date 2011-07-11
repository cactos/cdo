package org.eclipse.emf.cdo.tests.db4o;

import org.eclipse.emf.cdo.server.IRepository;
import org.eclipse.emf.cdo.server.IStore;
import org.eclipse.emf.cdo.tests.AllConfigs;
import org.eclipse.emf.cdo.tests.bugzilla.Bugzilla_261218_Test;
import org.eclipse.emf.cdo.tests.bugzilla.Bugzilla_324585_Test;
import org.eclipse.emf.cdo.tests.config.impl.ConfigTest;
import org.eclipse.emf.cdo.tests.config.impl.RepositoryConfig;

import java.util.List;
import java.util.Map;

import junit.framework.Test;
import junit.framework.TestSuite;

public class AllTestsMEMDB4O extends AllConfigs
{

  public static Test suite()
  {
    return new AllTestsMEMDB4O().getTestSuite("CDO Tests (MEMDB4O)");
  }

  @Override
  protected void initTestClasses(List<Class<? extends ConfigTest>> testClasses)
  {
    super.initTestClasses(testClasses);

    // Skipped because takes too much
    testClasses.remove(Bugzilla_261218_Test.class);
    testClasses.remove(Bugzilla_324585_Test.class);
  }

  @Override
  protected void initConfigSuites(TestSuite parent)
  {
    addScenario(parent, COMBINED, MemDB4ORepositoryConfig.INSTANCE, JVM, NATIVE);
  }

  public static class MemDB4ORepositoryConfig extends RepositoryConfig
  {
    public static final MemDB4ORepositoryConfig INSTANCE = new MemDB4ORepositoryConfig("DB4O");

    private static final long serialVersionUID = 1L;

    private transient boolean optimizing = false;

    public MemDB4ORepositoryConfig(String name)
    {
      super(name);
    }

    @Override
    protected void initRepositoryProperties(Map<String, String> props)
    {
      super.initRepositoryProperties(props);
      props.put(IRepository.Props.SUPPORTING_AUDITS, "false");
      props.put(IRepository.Props.SUPPORTING_BRANCHES, "false");
    }

    @Override
    protected IStore createStore(String repoName)
    {
      if (!isRestarting())
      {
        MEMDB4OStore.clearContainer();
      }
      return new MEMDB4OStore();
    }

    @Override
    protected boolean isOptimizing()
    {
      // Do NOT replace this with a hardcoded value!
      return optimizing;
    }
  }

}